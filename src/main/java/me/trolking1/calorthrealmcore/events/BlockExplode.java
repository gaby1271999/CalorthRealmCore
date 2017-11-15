package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.guilds.Guild;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

/**
 * Created by Gabriel on 4/4/2017.
 */
public class BlockExplode implements Listener {

    private FileConfiguration guildConfig = Main.getConfigManager().getGuild().getConfig();

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        for (String world : guildConfig.getStringList("guildworld")) {
            for (Block block : event.blockList()) {
                if (block.getWorld().getName().equals(world)) {
                    Location location = block.getLocation();
                    Guild guild = Main.getGuildManager().getGuild(location);
                    if (guild == null) {
                        if (!guildConfig.getBoolean("worldsettings.wilderness.blockexplode")) {
                            event.setCancelled(true);
                        }
                    } else {
                        //TODO
                    }
                }
            }
        }
    }
}
