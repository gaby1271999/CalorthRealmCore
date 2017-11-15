package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.events.entitymove.EntityMoveEvent;
import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import me.trolking1.calorthrealmcore.utils.ActionBarMessage;
import me.trolking1.calorthrealmcore.utils.DamageEffect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class EntityMove implements Listener {

    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        Entity entity = event.getEntity();

        if (Main.getCustomMobManager().containsEntity(entity)) {
            Main.getCustomMobManager().checkEntityInRegionLocation(entity);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (Main.getCustomMobManager().containsEntity(entity)) {
            Main.getCustomMobManager().removeMob(entity);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && !(event.getEntity() instanceof Player)) {
            if (event.getDamager().hasMetadata("playerdata")) {
                Entity entity = event.getEntity();
                if (Main.getCustomMobManager().containsEntity(entity)) {
                    if (!entity.hasMetadata("health")) {
                        entity.setMetadata("health", new FixedMetadataValue(Main.getMain(), Main.getCustomMobManager().getCustomMob(entity).getHealth() - event.getDamage()));
                    } else {
                        double health = entity.getMetadata("health").get(0).asDouble() - event.getDamage();

                        if (health > 0) {
                            entity.setMetadata("health", new FixedMetadataValue(Main.getMain(), health));
                        } else {
                            Main.getCustomMobManager().removeMob(entity);
                        }
                    }
                }
            }
        } else if (event.getDamager() instanceof Arrow && !(event.getEntity() instanceof Player)) {
            event.getDamager().remove();
            try {
                DamageEffect.class.newInstance().damageEffect(event.getEntity());
                ActionBarMessage.class.newInstance().actionBarMessage("test");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            event.getDamager().remove();
            try {
                DamageEffect.class.newInstance().damageEffect(event.getEntity());
                ActionBarMessage.class.newInstance().actionBarMessage("test");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (!(event.getDamager() instanceof Player) && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (player.hasMetadata("playerdata")) {
                PlayerData playerData = (PlayerData) player.getMetadata("playerdata").get(0).value();
                double health = playerData.getData().getHealth()-event.getDamage();
                playerData.getData().setHealth(health);

            }
        }

        event.setCancelled(true);
    }

}
