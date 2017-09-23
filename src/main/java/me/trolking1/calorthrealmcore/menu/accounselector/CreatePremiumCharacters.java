package me.trolking1.calorthrealmcore.menu.accounselector;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Gabriel on 6/29/2017.
 */
public class CreatePremiumCharacters implements Menu {

    private FileConfiguration accountSelectorConfig = Main.configManager.getAccountSelector().getConfig();
    private Inventory inv;

    public CreatePremiumCharacters() {
        inv = Bukkit.createInventory(null, accountSelectorConfig.getInt("premiumcharactersmenu.rows")*9, ChatColor.translateAlternateColorCodes('&', accountSelectorConfig.getString("premiumcharactersmenu.menuname")));

        if (accountSelectorConfig.get("premiumcharactersmenu.fillupitem") != null) {
            Item item = (Item) accountSelectorConfig.get("premiumcharactersmenu.fillupitem");
            for (int slot = 0; slot < inv.getSize(); slot++) {
                if (inv.getItem(slot) == null) {
                    inv.setItem(slot, item.getItem());
                }
            }
        }
    }

    public void openMenu(Player player) {
        player.openInventory(inv);
    }
}
