package me.trolking1.calorthrealmcore.menu.accounselector;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.menu.Menu;
import me.trolking1.calorthrealmcore.playerinfo.classes.Archer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Gabriel on 6/29/2017.
 */
public class CreateFreeCharacters implements Menu {

    private FileConfiguration accountSelectorConfig = Main.getConfigManager().getAccountSelector().getConfig();
    private Inventory inv;

    public CreateFreeCharacters() {
        inv = Bukkit.createInventory(null, accountSelectorConfig.getInt("freecharactersmenu.rows")*9, ChatColor.translateAlternateColorCodes('&', accountSelectorConfig.getString("freecharactersmenu.menuname")));

        for (String args : accountSelectorConfig.getStringList("freecharactersmenu.classes")) {
            String[] line = args.split("-");
            if (line[0].equalsIgnoreCase("archer")) {
                inv.setItem(Integer.valueOf(line[1]), ((Archer) Main.getConfigManager().getClasses().getConfig().get("archer")).getProfileItem().getItem());
            }
        }

        if (accountSelectorConfig.get("freecharactersmenu.fillupitem") != null) {
            Item item = (Item) accountSelectorConfig.get("freecharactersmenu.fillupitem");
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
