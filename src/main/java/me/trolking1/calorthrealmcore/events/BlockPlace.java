package me.trolking1.calorthrealmcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Gabriel on 4/4/2017.
 */
public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        event.setCancelled(Main.guildUtils.onBlockChange(player, event.getBlock().getLocation()));
        return;
    }
}
