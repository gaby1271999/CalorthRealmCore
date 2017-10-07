package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.menu.Item;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Gabriel on 4/14/2017.
 */
public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Main.playerInfoManager.setPlayerAccounts(player);

        if (Main.configManager.getConfig().getConfig().getBoolean("tptospawn") && Main.configManager.getConfig().getConfig().get("spawn") != null) {
            player.teleport((Location) Main.configManager.getConfig().getConfig().get("spawn"));
        }

        if ((player.getInventory().getItem(Main.configManager.getAccountSelector().getConfig().getInt("selector.slot")) == null ||
                player.getInventory().getItem(Main.configManager.getAccountSelector().getConfig().getInt("selector.slot")).getItemMeta().getDisplayName() == null ||
                player.getInventory().getItem(Main.configManager.getAccountSelector().getConfig().getInt("selector.slot")).getItemMeta().getDisplayName().equals(((Item) Main.configManager.getAccountSelector().getConfig().get("selector.item")).getItem().getItemMeta().getDisplayName()))) {

            player.getInventory().setItem(Main.configManager.getAccountSelector().getConfig().getInt("selector.slot"), ((Item) Main.configManager.getAccountSelector().getConfig().get("selector.item")).getItem());

        }
    }

}
