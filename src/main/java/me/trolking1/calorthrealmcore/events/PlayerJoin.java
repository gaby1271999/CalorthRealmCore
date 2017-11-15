package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.utils.ActionBarMessage;
import me.trolking1.calorthrealmcore.utils.DamageEffect;
import me.trolking1.calorthrealmcore.utils.TabList;
import org.bukkit.Bukkit;
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

        Main.getPlayerInfoManager().setPlayerAccounts(player);

        if (Main.getConfigManager().getConfig().getConfig().getBoolean("tptospawn") && Main.getConfigManager().getConfig().getConfig().get("spawn") != null) {
            player.teleport((Location) Main.getConfigManager().getConfig().getConfig().get("spawn"));
        }

        Main.getPlayerInfoManager().createAccountSelector(player);
    }

}
