package me.trolking1.calorthrealmcore.guilds.utils;

import me.trolking1.calorthrealmcore.Config;
import me.trolking1.calorthrealmcore.guilds.Guild;
import me.trolking1.calorthrealmcore.guilds.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 4/3/2017.
 */
public class GuildUtils {

    private FileConfiguration guildConfig = Main.configManager.getGuild().getConfig();

    public int createGuild(Player player, String guildName) {
        if (!Main.guildManager.isInOtherGuild(player)) {
            if (Main.econ.getBalance(player) >= Main.configManager.getConfig().getConfig().getInt("startguildmoney")) {
                if (Main.guildManager.guildNameExists(guildName)) {
                    if (Main.guildManager.freeSpaceArroundChunk(player.getLocation(), true)) {
                        Guild guild = new Guild(guildName, player.getLocation(), player.getLocation(), null, null);
                        Config config = new Config(Main.main, "/guilds/" + guildName, false);
                        config.getConfig().set("guild", guild);
                        config.saveConfig();
                        Main.guildManager.getGuilds().add(guild);
                        return 1;
                    } else {
                        return 2;
                    }
                } else {
                    return 3;
                }
            } else {
                return 4;
            }
        } else {
            return 5;
        }
    }

    public int removeGuild(Player player) {
        Guild guild = Main.guildManager.getGuildOfPlayer(player);

        if (guild != null) {
            Config config = Main.configManager.getGuildsFile(guild.getName());
            config.getFile().delete();
            Main.guildManager.getGuilds().remove(guild);
            return 1;
        } else {
            return 2;
        }
    }

    public Map<String, String> request = new HashMap<>();
    public Map<String, String> invitor = new HashMap<>();
    public int addCitizenRequest(final Player player, final Player citizen) {
        Guild guild = Main.guildManager.getGuildOfPlayer(player);

        if (guild != null) {
            if (!guild.getCitizens().containsPlayer(citizen)) {
                if (Main.guildManager.getGuildOfPlayer(citizen) == null) {
                    request.put(citizen.getName(), guild.getName());
                    invitor.put(citizen.getName(), player.getName());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.main, new Runnable() {
                        @Override
                        public void run() {
                            Main.messageManager.sendMessageFromConfig(citizen, "guild.addcitizen.requestovertime");
                            Main.messageManager.sendMessage(player, Main.messageManager.getMessageFromConfig("guild.addcitizen.ignorerequest").replace("%player%", citizen.getName()));
                            request.remove(citizen.getName());
                            invitor.remove(citizen.getName());
                        }
                    }, 20*Main.configManager.getMessages().getConfig().getInt("guild.addcitizen.overtime"));
                    return 1;
                } else {
                    return 2;
                }
            } else {
                return 3;
            }
        } else {
            return 4;
        }
    }

    public void addCitizen(Player citizen, boolean accepted) {
        if (accepted) {
            String guildName = request.get(citizen.getName());
            Guild guild = Main.guildManager.getGuild(guildName);
            guild.getCitizens().addCitizen(guildName, citizen);
            request.remove(citizen.getName());
            invitor.remove(citizen.getName());
        } else {
            request.remove(citizen.getName());
            invitor.remove(citizen.getName());
        }
    }

    public int remnoveCitizen(Player player, Player citizen) {
        Guild guild = Main.guildManager.getGuildOfPlayer(player);

        if (guild != null) {
            if (guild.getCitizens().containsPlayer(citizen)) {
                if (guild.getCitizens().removeCitizen(guild.getName(), citizen)) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                return 4;
            }
        } else {
            return 5;
        }
    }

    public boolean onBlockChange(Player player, Location location) {
        for (String world : guildConfig.getStringList("guildworld")) {
            if (player.getWorld().getName().equals(world)) {
                Guild guild = Main.guildManager.getGuild(location);
                if (guild == null) {
                    if (!guildConfig.getBoolean("worldsettings.wilderness.blockbreak")) {
                        return true;
                    }
                } else {
                    Plot plot = Main.guildManager.getPlot(location);

                    if (guild.getCitizens().getLeader().getName().equals(player.getName())) {
                        if (plot != null) {
                            if (plot.getOwner().getName().equals(player.getName()) || plot.getOwner() == null) {
                                return false;
                            } else {
                                return guildConfig.getBoolean("guildsettings.plot.leader.canbuildinboughtplot");

                            }
                        } else {
                            if (guild.inGuildWithoutPlots(location)) {
                                return false;
                            }
                        }
                    } else if (guild.getCitizens().getRank(player) == 2) {
                        if (plot != null) {
                            if (plot.getOwner() != null) {
                                return guildConfig.getBoolean("guildsettings.plot.officer.canbuildinplot");
                            } else if (plot.getOwner().getName().equals(player.getName())) {
                                return false;
                            } else {
                                return guildConfig.getBoolean("guildsettings.plot.officer.canbuildinboughtplot");
                            }
                        } else {
                            if (guild.inGuildWithoutPlots(location)) {
                                return guildConfig.getBoolean("guildsettings.plot.officer.canbuildinguild");
                            }
                        }
                    } else if (guild.getCitizens().getRank(player) == 3) {
                        if (plot != null) {
                            if (plot.getOwner() != null) {
                                return guildConfig.getBoolean("guildsettings.plot.member.canbuildinplot");
                            } else if (plot.getOwner().getName().equals(player.getName())) {
                                return false;
                            } else {
                                return guildConfig.getBoolean("guildsettings.plot.member.canbuildinboughtplot");
                            }
                        } else {
                            if (guild.inGuildWithoutPlots(location)) {
                                return guildConfig.getBoolean("guildsettings.plot.member.canbuildinguild");
                            }
                        }
                    } else if (guild.getCitizens().getRank(player) == 4) {
                        if (plot != null) {
                            if (plot.getOwner() != null) {
                                return guildConfig.getBoolean("guildsettings.plot.guest.canbuildinplot");
                            } else if (plot.getOwner().getName().equals(player.getName())) {
                                return false;
                            } else {
                                return guildConfig.getBoolean("guildsettings.plot.guest.canbuildinboughtplot");
                            }
                        } else {
                            if (guild.inGuildWithoutPlots(location)) {
                                return guildConfig.getBoolean("guildsettings.plot.guest.canbuildinguild");
                            }
                        }
                    } else if (guild.getCitizens().getRank(player) == 0) {
                        return guildConfig.getBoolean("guildsettings.plot.stranger.canbuildinguild");
                    }
                }
            }
        }

        return false;
    }
}
