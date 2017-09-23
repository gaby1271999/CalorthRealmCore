package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import me.trolking1.calorthrealmcore.playerinfo.ability.archer.Windstorm;
import me.trolking1.calorthrealmcore.playerinfo.classes.Archer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Gabriel on 5/31/2017.
 */
public class Bow implements Listener {

    @EventHandler
    public void onBowShoot(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
            Player player = (Player) event.getEntity();

            PlayerData playerData = Main.playerInfoManager.getPlayerData(player);
            if (playerData.getClass().getName().equals("Archer")) {
                /*
                Archer archer = (Archer) playerData.getPlayerClass().getClass();
                if (archer.getCurrentAbility() != null) {
                    if (archer.getCurrentAbility().getClass().getName().equals("Windstorm")) {
                        Windstorm windStorm = (Windstorm) archer.getCurrentAbility();
                        windStorm.setPlayerHitEffect(player);
                        return;
                    }
                }*/
            }

        }
    }
}
