package me.trolking1.calorthrealmcore.database;

import com.zaxxer.hikari.HikariDataSource;
import me.trolking1.calorthrealmcore.ConfigManager;
import me.trolking1.calorthrealmcore.playerinfo.Data;
import me.trolking1.calorthrealmcore.playerinfo.classes.PlayerClass;
import me.trolking1.calorthrealmcore.guilds.Citizens;
import me.trolking1.calorthrealmcore.guilds.GuildState;
import me.trolking1.calorthrealmcore.playerinfo.LevelUp;
import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import me.trolking1.calorthrealmcore.playerinfo.Skill;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.*;

/**
 * Created by Gabriel on 3/29/2017.
 */
public class Database {

    private HikariDataSource hikari = Main.hikari;
    private ConfigManager configManager = Main.configManager;

    public boolean createDBConnection() {
        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");

        hikari.addDataSourceProperty("serverName", this.configManager.getConfig().getConfig().getString("database.host"));
        hikari.addDataSourceProperty("port", "3306");
        hikari.addDataSourceProperty("databaseName", this.configManager.getConfig().getConfig().getString("database.name"));
        hikari.addDataSourceProperty("user", this.configManager.getConfig().getConfig().getString("database.username"));
        hikari.addDataSourceProperty("password", this.configManager.getConfig().getConfig().getString("database.password"));

        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()) {

            //guilds
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS guilds(id INT(4) AUTO_INCREMENT, name VARCHAR(10) NOT NULL, leader_uuid VARCHAR(36) NOT NULL, PRIMARY KEY(id));");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS guilds_officers(guild_id INT(4) NOT NULL, player_uuid VARCHAR(36) NOT NULL);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS guilds_members(guild_id INT(4) NOT NULL, player_uuid VARCHAR(36) NOT NULL);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS guilds_guests(guild_id INT(4) NOT NULL, player_uuid VARCHAR(36) NOT NULL);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS guilds_inwar(guild_id INT(4) NOT NULL, other_guild_id INT(4) NOT NULL);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS guilds_ally(guild_id INT(4) NOT NULL, other_guild_id INT(4) NOT NULL);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS guilds_neutral(guild_id INT(4) NOT NULL, other_guild_id INT(4) NOT NULL);");

            //player data
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_data(id INT(4) AUTO_INCREMENT, player_uuid VARCHAR(36) NOT NULL, last_used INT(1), PRIMARY KEY(id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS level(id INT(4) AUTO_INCREMENT, lvl INT(4), xp INT(4), player_data_id INT(4) NOT NULL, PRIMARY KEY(id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_class(id INT(4) AUTO_INCREMENT, class INT(2) NOT NULL, player_data_id INT(4) NOT NULL, PRIMARY KEY(id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_skill(id INT(4) AUTO_INCREMENT, skillpoints INT(4), strength INT(4), dexterity INT(4), intelligence INT(4), defense INT(4), agility INT(4), player_data_id INT(4) NOT NULL, PRIMARY KEY(id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS data(id INT(4) AUTO_INCREMENT, location TEXT(65000), inventory TEXT(65000), player_data_id INT(4) NOT NULL, PRIMARY KEY(id))");

            connection.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public String getGuildNameById(int id) {
        try (Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery("SELECT * FROM guilds WHERE id=" + id + ";");

            return resultSet.getString("name");
        } catch (Exception exception) {
            return null;
        }
    }

    public int getGuildIdByName(String name) {
        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery("SELECT * FROM guilds WHERE name='" + name + "';");

            return resultSet.getInt("id");
        } catch (Exception exception) {
            return 0;
        }
    }

    /*

    "BEGIN IF NOT EXISTS (SELECT * FROM guilds WHERE name='" + guildName + "') BEGIN INSERT INTO guilds VALUES('" + guildName + "', '" + citizens.getLeader().getUniqueId().toString() +"') END END"

     */

    public boolean addCitizen(String guildName, Player player, int rank) {
        try (Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement()) {

            String table = "";

            switch (rank) {
                case 1:
                    table = "guilds";
                case 2:
                    table = "guilds_officers";
                case 3:
                    table = "guilds_members";
                case 4:
                    table = "guilds_guests";
            }

            if (table.equals("guilds")) {
                if (getGuildIdByName(guildName) != 0) {
                    statement.executeUpdate("INSERT INTO guilds(name, leader_uuid) VALUES('" + guildName + "', '" + player.getUniqueId().toString() + "')");
                    return true;
                } else {
                    return false;
                }
            } else {
                if (!isACitizen(guildName, player, table) && getGuildIdByName(guildName) != 0) {
                    statement.executeUpdate("INSERT INTO " + table + "(guild_id, player_uuid) VALUES(" + getGuildIdByName(guildName) + ", '" + player.getUniqueId().toString() + "')");
                    return true;
                } else {
                    return  false;
                }
            }
        } catch (Exception exception) {
            return  false;
        }
    }

    public boolean removeCitizen(String guildName, Player player, int rank) {
        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()) {

            String table = "";

            switch (rank) {
                case 2:
                    table = "guilds_officers";
                case 3:
                    table = "guilds_members";
                case 4:
                    table = "guilds_guests";
            }

            if (isACitizen(guildName, player, table) && getGuildIdByName(guildName) != 0) {
                statement.executeUpdate("DELETE FROM " + table + " WHERE guild_id=" + getGuildIdByName(guildName) + " AND player_uuid='" + player.getUniqueId().toString() + "'");
                return true;
            } else {
                return  false;
            }
        } catch (Exception exception) {
            return  false;
        }
    }

    public boolean addWarState(String guildName, int state, String effectedGuildName) {
        try (Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement()) {

            int guildId = getGuildIdByName(guildName);
            int effectedGuildId = getGuildIdByName(effectedGuildName);

            if (guildId != 0 && effectedGuildId != 0) {
                if (state == 1) {
                    if (getGuildState(guildId, effectedGuildId) == null) {
                        statement.executeUpdate("INSERT INTO guilds_neutral(guild_id, other_guild_id) VALUES(" + guildId + ", " + effectedGuildId + ")");
                        return true;
                    } else {
                        statement.executeUpdate("INSERT INTO guilds_neutral(guild_id, other_guild_id) VALUES(" + guildId + ", " + effectedGuildId + ")");
                        statement.executeUpdate("DELETE FROM " + getGuildState(guildId, effectedGuildId) + " WHERE guild_id=" + guildId + " AND other_guild_id=" + effectedGuildId);
                        return true;
                    }
                } else if (state == 2) {
                    statement.executeUpdate("INSERT INTO guilds_ally(guild_id, other_guild_id) VALUES(" + guildId + ", " + effectedGuildId + ")");
                    statement.executeUpdate("DELETE FROM " + getGuildState(guildId, effectedGuildId) + " WHERE guild_id=" + guildId + " AND other_guild_id=" + effectedGuildId);
                    return true;
                } else if (state == 3) {
                    statement.executeUpdate("INSERT INTO guilds_inwar(guild_id, other_guild_id) VALUES(" + guildId + ", " + effectedGuildId + ")");
                    statement.executeUpdate("DELETE FROM " + getGuildState(guildId, effectedGuildId) + " WHERE guild_id=" + guildId + " AND other_guild_id=" + effectedGuildId);
                    return  true;
                }
            }

        } catch (Exception exception) {
            return false;
        }
        return false;
    }

    public Citizens getCitizens(String guildName) {
        try (Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet resultSetGuilds = statement.executeQuery("SELECT * FROM guilds WHERE name='" + guildName);

            while (resultSetGuilds.next()) {
                int id = resultSetGuilds.getInt("id");
                String leader = resultSetGuilds.getString("leader_uuid");

                List<String> officers = new ArrayList<>();
                ResultSet resultSetGuildsOfficers = statement.executeQuery("SELECT * FROM guilds_officers WHERE guild_id=" + id);
                while (resultSetGuildsOfficers.next()) {
                    officers.add(resultSetGuildsOfficers.getString("player_uuid"));
                }

                List<String> members = new ArrayList<>();
                ResultSet resultSetGuildsMembers = statement.executeQuery("SELECT * FROM guilds_members WHERE guild_id=" + id);
                while (resultSetGuildsMembers.next()) {
                    members.add(resultSetGuildsMembers.getString("player_uuid"));
                }

                List<String> guests = new ArrayList<>();
                ResultSet resultSetGuildsGuests = statement.executeQuery("SELECT * FROM guilds_guests WHERE guild_id=" + id);
                while (resultSetGuildsGuests.next()) {
                    guests.add(resultSetGuildsGuests.getString("player_uuid"));
                }

                return new Citizens(leader, officers, officers, officers);
            }
        } catch (Exception exception) {
            return null;
        }

        return null;
    }

    public GuildState getGuildState(String guildName) {
        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSetGuilds = statement.executeQuery("SELECT * FROM guilds WHERE name='" + guildName);

            while (resultSetGuilds.next()) {
                int id = resultSetGuilds.getInt("id");

                List<String> inwar = new ArrayList<>();
                ResultSet resultSetInWar = statement.executeQuery("SELECT * FROM guilds_inwar WHERE guild_id=" + id);
                while (resultSetInWar.next()) {
                    inwar.add(getGuildNameById(resultSetInWar.getInt("other_guild_id")));
                }

                List<String> ally = new ArrayList<>();
                ResultSet resultSetAlly = statement.executeQuery("SELECT * FROM guilds_ally WHERE guild_id=" + id);
                while (resultSetAlly.next()) {
                    ally.add(getGuildNameById(resultSetAlly.getInt("other_guild_id")));
                }

                List<String> neutral = new ArrayList<>();
                ResultSet resultSetNeutral = statement.executeQuery("SELECT * FROM guilds_neutral WHERE guild_id=" + id);
                while (resultSetNeutral.next()) {
                    neutral.add(getGuildNameById(resultSetNeutral.getInt("other_guild_id")));
                }

                return new GuildState(inwar, ally, neutral);
            }
        } catch (Exception exception) {
            return null;
        }

        return null;
    }

    public boolean isACitizen(String guildName, Player player, String table) {
        try (Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table + " WHERE guild_id=" + getGuildIdByName(guildName));

            while (resultSet.next()) {
                if (resultSet.getString("player_uuid").equals(player.getUniqueId().toString())) {
                    return true;
                }
            }
        } catch (Exception exception) {
            return false;
        }

        return false;
    }

    public String getGuildState(int guildId, int effectedGuildId) {
        try (Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet neutral = statement.executeQuery("SELECT * FROM guilds_neutral WHERE guild_id='" + guildId + "'");
            while (neutral.next()) {
                if (neutral.getInt("other_guild_id") == effectedGuildId) {
                    return "guilds_neutral";
                }
            }

            ResultSet ally = statement.executeQuery("SELECT * FROM guilds_ally WHERE guild_id='" + guildId + "'");
            while (ally.next()) {
                if (ally.getInt("other_guild_id") == effectedGuildId) {
                    return "guilds_ally";
                }
            }

            ResultSet inwar = statement.executeQuery("SELECT * FROM guilds_inwar WHERE guild_id='" + guildId + "'");
            while (inwar.next()) {
                if (inwar.getInt("other_guild_id") == effectedGuildId) {
                    return "guilds_inwar";
                }
            }

        } catch (Exception exception) {
            return null;
        }

        return null;
    }

    public int createAccount(Player player, PlayerData playerData) {
        try (Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement()) {

            changeLatest(player);
            statement.executeUpdate("INSERT INTO player_data(player_uuid, last_used) VALUES('" + player.getUniqueId().toString() + "', 1)");
            ResultSet rs = statement.executeQuery("SELECT id FROM player_data WHERE player_uuid='" + player.getUniqueId().toString() + "' AND last_used=1");
            int playerDataId;
            if (rs.next()) {
                playerDataId = rs.getInt("id");
            } else {
                return 0;
            }
            statement.executeUpdate("INSERT INTO level(lvl, xp, player_data_id) VALUES(" + playerData.getLevelUp().getXp() + ", " + playerData.getLevelUp().getXp() + ", " + playerDataId + ")");
            statement.executeUpdate("INSERT INTO player_class(class, player_data_id) VALUES(" + Main.playerInfoManager.getIntPlayerClass(playerData.getPlayerClass()) + ", " + playerDataId + ")");
            statement.executeUpdate("INSERT INTO player_skill(skillpoints, strength, dexterity, intelligence, defense, agility, player_data_id) VALUES(" + playerData.getSkill().getSkillpoints() + ", " + playerData.getSkill().getStrength() + ", " + playerData.getSkill().getDexterity() + ", " + playerData.getSkill().getIntelligence() + ", " + playerData.getSkill().getDefense() + ", " + playerData.getSkill().getAgility() + ", " + playerDataId + ")");
            statement.executeUpdate("INSERT INTO data(location, inventory, player_data_id) VALUES('" + Main.encodeObject(playerData.getData().getLocation()) + "', '" + Main.encodeInventory(playerData.getData().getItems()) + "', " + playerDataId + ")");
            return playerDataId;
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public void changeLatest(Player player) {
        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("UPDATE player_data SET last_used=0 WHERE player_uuid='" + player.getUniqueId().toString() + "' AND last_used=1");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setLastUsed(int playerId) {
        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("UPDATE player_data SET last_used=1 WHERE id='" + String.valueOf(playerId) + "'");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Map<Integer, Skill> getPlayerSkill(Player player) {
        try (Connection connection = hikari.getConnection();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement()) {

            ResultSet playerAccounts = statement1.executeQuery("SELECT * FROM player_data WHERE player_uuid='" + player.getUniqueId().toString() + "'");
            Map<Integer, Skill> accountSkills = new HashMap<>();
            while (playerAccounts.next()) {
                ResultSet resultSet = statement2.executeQuery("SELECT * FROM player_skill WHERE player_data_id=" + playerAccounts.getInt("id"));
                if (resultSet.next()) {
                    Skill skill = new Skill(resultSet.getInt("skillpoints"), resultSet.getInt("strength"), resultSet.getInt("dexterity"), resultSet.getInt("intelligence"), resultSet.getInt("defense"), resultSet.getInt("agility"));
                    accountSkills.put(playerAccounts.getInt("id"), skill);
                }
            }
            return accountSkills;
        } catch (Exception exception) {
            exception.printStackTrace();
            return  null;
        }
    }

    public Map<Integer, PlayerClass> getPlayerClass(Player player) {
        try (Connection connection = hikari.getConnection();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement()) {

            ResultSet playerAccounts = statement1.executeQuery("SELECT * FROM player_data WHERE player_uuid='" + player.getUniqueId().toString() + "'");
            Map<Integer, PlayerClass> accountClasses = new HashMap<>();
            while (playerAccounts.next()) {
                ResultSet resultSet = statement2.executeQuery("SELECT * FROM player_class WHERE player_data_id=" + playerAccounts.getInt("id"));
                if (resultSet.next()) {
                    PlayerClass playerClass = Main.playerInfoManager.getPlayerClass(resultSet.getInt("class"));
                    accountClasses.put(playerAccounts.getInt("id"), playerClass);
                }
            }
            return accountClasses;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public Map<Integer, LevelUp> getPlayerLevel(Player player) {
        try (Connection connection = hikari.getConnection();
             Statement statement1 = connection.createStatement();
             Statement statement2 = connection.createStatement()) {

            ResultSet playerAccounts = statement1.executeQuery("SELECT * FROM player_data WHERE player_uuid='" + player.getUniqueId().toString() + "'");
            Map<Integer, LevelUp> accountLevels = new HashMap<>();
            while (playerAccounts.next()) {
                ResultSet resultSet = statement2.executeQuery("SELECT * FROM level WHERE player_data_id=" + playerAccounts.getInt("id"));
                if (resultSet.next()) {
                    int playerLevel = resultSet.getInt("lvl");
                    int playerXP = resultSet.getInt("xp");
                    accountLevels.put(playerAccounts.getInt("id"), new LevelUp(playerLevel, playerXP));
                }
            }
            return accountLevels;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public Map<Integer, Data> getPlayerData(Player player) {
        try (Connection connection = hikari.getConnection();
             Statement statement1 = connection.createStatement();
             Statement statement2 = connection.createStatement()) {

            ResultSet playerAccounts = statement1.executeQuery("SELECT * FROM player_data WHERE player_uuid='" + player.getUniqueId().toString() + "'");
            Map<Integer, Data> datas = new HashMap<>();
            while (playerAccounts.next()) {
                ResultSet resultSet = statement2.executeQuery("SELECT * FROM data WHERE player_data_id=" + playerAccounts.getInt("id"));
                if (resultSet.next()) {
                    Location location = (Location) Main.decodeObject(resultSet.getString("location"));
                    ItemStack[] items = Main.decodeInventory(resultSet.getString("inventory"));

                    datas.put(playerAccounts.getInt("id"), new Data(location, items));
                }
            }
            return datas;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public Map<Integer, Boolean> getLastUsed(Player player) {
        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet playerAccounts = statement.executeQuery("SELECT * FROM player_data WHERE player_uuid='" + player.getUniqueId().toString() + "'");
            Map<Integer, Boolean> playerLatestUsed = new HashMap<>();
            while (playerAccounts.next()) {
                int lastUsed = playerAccounts.getInt("last_used");
                playerLatestUsed.put(playerAccounts.getInt("id"), lastUsed == 1 ? true : false);
            }
            return playerLatestUsed;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public int getAmountOfAccounts(Player player) {
        try (Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement()) {

            int counter = 0;
            ResultSet playerAccounts = statement.executeQuery("SELECT * FROM player_data WHERE player_uuid='" + player.getUniqueId().toString() + "'");
            while (playerAccounts.next()) {
                counter++;
            }
            return counter;
        } catch (Exception exception) {
            return  0;
        }
    }

    public void saveAccount(Player player) {
        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()) {
            int playerId = (int) player.getMetadata("id").get(0).value();
            PlayerData playerData = (PlayerData) player.getMetadata("playerdata").get(0).value();

            statement.executeUpdate("UPDATE level SET lvl=" + playerData.getLevelUp().getLevel() + ", xp=" + playerData.getLevelUp().getXp() + " WHERE player_data_id='" + playerId + "'");
            statement.executeUpdate("UPDATE player_skill SET skillpoints=" + playerData.getSkill().getSkillpoints() + ", strength=" + playerData.getSkill().getStrength() + ", dexterity=" + playerData.getSkill().getDexterity() + ", intelligence="+ playerData.getSkill().getIntelligence() + ", defense=" + playerData.getSkill().getDefense() + ", agility=" + playerData.getSkill().getAgility() + " WHERE player_data_id='" + playerId + "'");
            statement.executeUpdate("UPDATE data SET location='" + Main.encodeObject(player.getLocation()) + "', inventory='" + Main.encodeInventory(player.getInventory().getContents()) + "' WHERE player_data_id='" + playerId + "'");
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
