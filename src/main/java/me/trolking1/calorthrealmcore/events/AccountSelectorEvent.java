package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.playerinfo.classes.Archer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

/**
 * Created by Gabriel on 6/29/2017.
 */
public class AccountSelectorEvent implements Listener {

    private FileConfiguration accountSelectorConfig = Main.configManager.getAccountSelector().getConfig();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getCurrentItem().getType() == null) {
            return;
        }

        if (event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (event.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', accountSelectorConfig.getString("menuname")))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName() != null) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(((Item) accountSelectorConfig.get("nonpremiumemptyplace")).getItem().getItemMeta().getDisplayName())) {
                    Main.menuManager.getCreateFreeCharacters().openMenu(player);
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(((Item) accountSelectorConfig.get("premiumemptyplace.permission")).getItem().getItemMeta().getDisplayName())) {
                    Main.menuManager.getCreatePremiumCharacters().openMenu(player);
                } else if (!player.getMetadata("slotid").isEmpty()) {
                    Map<Integer, Integer> slotids = (Map<Integer, Integer>) player.getMetadata("slotid").get(0).value();
                    for (int slot : slotids.keySet()) {
                        if (event.getSlot() == slot) {
                            Main.playerInfoManager.loginPlayer(player, slotids.get(slot));
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
            event.setCancelled(true);
            return;
        } else if (event.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', accountSelectorConfig.getString("freecharactersmenu.menuname")))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', ((Archer) Main.configManager.getClasses().getConfig().get("archer")).getProfileItem().getItem().getItemMeta().getDisplayName()))) {
                Main.playerInfoManager.createAccount(player, (Archer) Main.configManager.getClasses().getConfig().get("archer"));
                player.closeInventory();
            }
            event.setCancelled(true);
            return;
        } else if (event.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', accountSelectorConfig.getString("premiumcharactersmenu.menuname")))) {

            event.setCancelled(true);
            return;
        }
    }
}
