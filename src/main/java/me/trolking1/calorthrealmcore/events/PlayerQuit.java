package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.Main;
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
            Main.getDatabase().saveAccount(player);
            player.getInventory().clear();
            player.removeMetadata("id", Main.getMain());
            player.removeMetadata("playerdata", Main.getMain());
        }
    }
}
