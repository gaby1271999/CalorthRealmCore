package me.trolking1.calorthrealmcore.playerinfo;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.playerinfo.classes.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 4/10/2017.
 */
public class PlayerInfoManager {

    private Map<Integer, PlayerData>  playerCharacters;

    public PlayerInfoManager() {
        playerCharacters = new HashMap<>();
    }

    public Map<Integer, PlayerData> getPlayerAccounts(Player player) {
        Map<Integer, PlayerData> playerAccounts = null;
        if (playerCharacters != null) {
            playerAccounts = new HashMap<>();
            for (int id : playerCharacters.keySet()) {
                if (playerCharacters.get(id).getUuid().toString().equals(player.getUniqueId().toString())) {
                    playerAccounts.put(id, playerCharacters.get(id));
                }
            }
        }

        return playerAccounts;
    }

    public void setPlayerAccounts(Player player) {
        Map<Integer, LevelUp> playerLevels = Main.getDatabase().getPlayerLevel(player);
        Map<Integer, PlayerClass> playerClasses = Main.getDatabase().getPlayerClass(player);
        Map<Integer, Skill> playerSkills = Main.getDatabase().getPlayerSkill(player);
        Map<Integer, Data> datas = Main.getDatabase().getPlayerData(player);

        if (playerLevels != null) {
            for (int id : playerLevels.keySet()) {
                playerCharacters.put(id, new PlayerData(player.getUniqueId(), playerLevels.get(id), playerClasses.get(id), playerSkills.get(id), datas.get(id), false));
            }
        }
    }

    public PlayerData getPlayerData(Player player) {
        if (playerCharacters != null) {
            for (int id : playerCharacters.keySet()) {
                PlayerData playerData = playerCharacters.get(id);
                if (playerData.isUsing()) {
                    return playerData;
                }
            }
        }

        return null;
    }

    public void loginPlayer(Player player, int accountId) {
        PlayerData playerData = playerCharacters.get(accountId);

        if (playerData.getData().getLocation() != null) {
            player.teleport(playerData.getData().getLocation());
        } else {
            Location tutorialSpawn = (Location) Main.getConfigManager().getConfig().getConfig().get("tutorialspawn");
            if (tutorialSpawn != null) {
                player.teleport(tutorialSpawn);
            } else {
                player.sendMessage(ChatColor.RED + "Tutorialspawn was not set.");
                return;
            }
        }

        player.getInventory().clear();

        ItemStack[] items = playerData.getData().getItems();
        for (int slot = 0; slot < items.length; slot++) {
            player.getInventory().setItem(slot, items[slot]);
        }

        playerData.setUsing(true);

        Main.getDatabase().changeLatest(player);
        Main.getDatabase().setLastUsed(accountId);

        player.setMetadata("id", new FixedMetadataValue(Main.getMain(), accountId));
        player.setMetadata("playerdata", new FixedMetadataValue(Main.getMain(), playerData));
    }

    public boolean createAccount(Player player, PlayerClass playerClass) {
        ItemStack[] items = new ItemStack[35];

        items[0] = playerClass.getMainItem().getItem();

        int counter = 1;
        for (Item item : playerClass.getItems()) {
            items[counter] = item.getItem();
            counter++;
        }

        PlayerData playerData = new PlayerData(player.getUniqueId(), new LevelUp(0, 0), playerClass, new Skill(0, 0, 0, 0, 0, 0), new Data((Location) Main.getConfigManager().getConfig().getConfig().get("tutorialspawn"), items, playerClass.getBeginHealth()), false);
        int playerDataId = Main.getDatabase().createAccount(player, playerData);
        if (playerDataId != 0) {
            playerCharacters.put(playerDataId, playerData);
            return true;
        }

        return false;
    }

    public boolean loggedIn(Player player) {
        Map<Integer, PlayerData> playerAccounts = getPlayerAccounts(player);
        for (Integer id : playerAccounts.keySet()) {
            PlayerData playerData = playerAccounts.get(id);
            if (playerData.isUsing()) {
                return true;
            }
        }

        return false;
    }

    public int getIntPlayerClass(PlayerClass playerClass) {
        if (playerClass.getClass().getSimpleName().equals("Archer")) {
            return 1;
        } else if (playerClass.getClass().getSimpleName().equals("Paladin")) {
            return 2;
        } else if (playerClass.getClass() == Rogue.class) {
            return 3;
        } else if (playerClass.getClass() == Wizard.class) {
            return 4;
        }

        return 0;
    }

    public PlayerClass getPlayerClass(int playerClassId) {
        if (playerClassId == 1) {
            return (Archer) Main.getConfigManager().getClasses().getConfig().get("archer");
        } else if (playerClassId == 2) {
            return (Paladin) Main.getConfigManager().getClasses().getConfig().get("paladin");
        } else if (playerClassId == 3) {
            return (Rogue) Main.getConfigManager().getClasses().getConfig().get("rogue");
        } else if (playerClassId == 4) {
            return (Wizard) Main.getConfigManager().getClasses().getConfig().get("wizard");
        }

        return null;
    }

    public void createAccountSelector(Player player) {
        if ((player.getInventory().getItem(Main.getConfigManager().getAccountSelector().getConfig().getInt("selector.slot")) == null ||
                player.getInventory().getItem(Main.getConfigManager().getAccountSelector().getConfig().getInt("selector.slot")).getItemMeta().getDisplayName() == null ||
                !player.getInventory().getItem(Main.getConfigManager().getAccountSelector().getConfig().getInt("selector.slot")).getItemMeta().getDisplayName().equals(((Item) Main.getConfigManager().getAccountSelector().getConfig().get("selector.item")).getItem().getItemMeta().getDisplayName()))) {

            player.getInventory().setItem(Main.getConfigManager().getAccountSelector().getConfig().getInt("selector.slot"), ((Item) Main.getConfigManager().getAccountSelector().getConfig().get("selector.item")).getItem());

        }
    }
}
