package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.events.entitymove.EntityMoveEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityMove implements Listener {

    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        Entity entity = event.getEntity();

        if (Main.customMobManager.containsEntity(entity)) {
            Main.customMobManager.checkEntityInRegionLocation(entity);
        }
    }

}
