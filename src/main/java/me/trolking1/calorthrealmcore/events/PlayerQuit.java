package me.trolking1.calorthrealmcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Gabriel on 4/16/2017.
 */
public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!player.getMetadata("id").isEmpty()) {
            Main.database.saveAccount(player);
            player.getInventory().clear();
            player.removeMetadata("id", Main.main);
            player.removeMetadata("playerdata", Main.main);
        }
    }
}
