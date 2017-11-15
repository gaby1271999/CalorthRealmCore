package me.trolking1.calorthrealmcore.events.entitymove;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityMoveManager {

    Main main = Main.getMain();

    public EntityMoveManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            public void run() {
                for (World world : Bukkit.getServer().getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        EntityMoveEvent entityMoveEvent = new EntityMoveEvent(entity);

                        if (entityMoveEvent.getEntity().getType() != EntityType.PLAYER) {
                            if (entityMoveEvent.moved()) {
                                Bukkit.getServer().getPluginManager().callEvent(entityMoveEvent);

                                if (entityMoveEvent.isCancelled()) {
                                    entityMoveEvent.teleportToOldLocation();
                                } else {
                                    entityMoveEvent.addNewLocation();
                                }
                            }
                        }
                    }
                }
            }
        }, 1, 0);
    }
}
