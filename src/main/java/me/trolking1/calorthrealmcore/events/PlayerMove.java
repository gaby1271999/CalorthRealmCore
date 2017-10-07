package me.trolking1.calorthrealmcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Gabriel on 4/16/2017.
 */
public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        for (String world : Main.configManager.getGuild().getConfig().getStringList("guildworld")) {
            if (player.getWorld().getName().equals(world)) {
                Main.guildManager.sendPlayerBoss(player);
            }
        }
    }
}
