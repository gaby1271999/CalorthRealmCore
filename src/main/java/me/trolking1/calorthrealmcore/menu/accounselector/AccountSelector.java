package me.trolking1.calorthrealmcore.menu.accounselector;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.menu.Menu;
import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import me.trolking1.calorthrealmcore.playerinfo.classes.Archer;
import me.trolking1.calorthrealmcore.playerinfo.classes.PlayerClass;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountSelector implements Menu {


    private FileConfiguration accountSelectorConfig = Main.configManager.getAccountSelector().getConfig();
    private Inventory inv;

    public AccountSelector(Player player) throws IllegalAccessException, InstantiationException {
        Map<Integer, Integer> slotId = new HashMap<>();
        Map<Integer, PlayerData> playerdatas = Main.playerInfoManager.getPlayerAccounts(player);

        inv = Bukkit.createInventory(null, accountSelectorConfig.getInt("rows")*9, ChatColor.translateAlternateColorCodes('&', accountSelectorConfig.getString("menuname")));

        String[] nonPremiumSlots = accountSelectorConfig.getString("nonpremiumslots").split("-");
        int indexNonPremium = 0;
        String[] premiumSlots = accountSelectorConfig.getString("premiumslots").split("-");
        int indexPremium = 0;

        if (playerdatas != null) {
            for (int id : playerdatas.keySet()) {
                PlayerData playerData = playerdatas.get(id);
                if (playerdatas.get(id).getPlayerClass().getClass().getSimpleName().equals("Archer")) {
                    Archer archer = (Archer) playerData.getPlayerClass();
                    ItemStack item = archer.getProfileItem().getItem();
                    inv.setItem(Integer.valueOf(nonPremiumSlots[indexNonPremium]), item);
                    slotId.put(Integer.valueOf(nonPremiumSlots[indexNonPremium]), id);
                    indexNonPremium++;
                } else {
                    inv.setItem(Integer.valueOf(premiumSlots[indexPremium]), new ItemStack(Material.DIAMOND_SWORD, 1));
                    slotId.put(Integer.valueOf(premiumSlots[indexPremium]), id);
                    indexPremium++;
                }
            }
        }

        if (indexNonPremium != nonPremiumSlots.length) {
            while(indexNonPremium != nonPremiumSlots.length) {
                inv.setItem(Integer.valueOf(nonPremiumSlots[indexNonPremium]), ((Item) accountSelectorConfig.get("nonpremiumemptyplace")).getItem());
                indexNonPremium++;
            }
        }


        if (indexPremium != premiumSlots.length) {
            int maxPremiumSlots = 0;
            String[] groups = Main.perms.getPlayerGroups(player);
            for (int i = 0; i < groups.length; i++) {
                for (String arg : accountSelectorConfig.getStringList("ranksperms")) {
                    String[] line = arg.split("-");
                    if (groups[i].equalsIgnoreCase(line[0])) {
                        if (Integer.valueOf(line[1]) > maxPremiumSlots) {
                            maxPremiumSlots = Integer.valueOf(line[1]);
                        }
                    }
                }
            }

            maxPremiumSlots-=indexPremium;
            while(indexPremium != premiumSlots.length) {
                if (maxPremiumSlots > 0) {
                    inv.setItem(Integer.valueOf(premiumSlots[indexPremium]), ((Item) accountSelectorConfig.get("premiumemptyplace.permission")).getItem());
                    indexPremium++;
                    maxPremiumSlots--;
                } else {
                    inv.setItem(Integer.valueOf(premiumSlots[indexPremium]), ((Item) accountSelectorConfig.get("premiumemptyplace.nopermission")).getItem());
                    indexPremium++;
                }
            }
        }

        if (accountSelectorConfig.get("fillupitem") != null) {
            Item item = (Item) accountSelectorConfig.get("fillupitem");
            for (int slot = 0; slot < inv.getSize(); slot++) {
                if (inv.getItem(slot) == null) {
                    inv.setItem(slot, item.getItem());
                }
            }
        }

        player.setMetadata("slotid", new FixedMetadataValue(Main.main, slotId));
    }

    public void openMenu(Player player) {
        player.openInventory(inv);
    }
}
