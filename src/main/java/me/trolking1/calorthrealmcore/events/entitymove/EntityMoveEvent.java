package me.trolking1.calorthrealmcore.events.entitymove;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

import java.util.HashMap;
import java.util.Map;

public class EntityMoveEvent extends EntityEvent {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private static Map<Integer, Location> previousLocations = new HashMap<Integer, Location>();

    public EntityMoveEvent(Entity entity) {
        super(entity);
    }

    public boolean moved() {
        if (entity instanceof LivingEntity) {
            if (entity.getType() != EntityType.PLAYER) {
                if (previousLocations.get(entity.getEntityId()) != null) {
                    Location previousLocation = previousLocations.get(this.entity.getEntityId());
                    if (previousLocation != entity.getLocation()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    previousLocations.put(getEntity().getEntityId(), getEntity().getLocation());
                    return false;
                }
            }
        }

        return false;
    }

    public void addNewLocation() {
        previousLocations.put(getEntity().getEntityId(), getEntity().getLocation());
    }

    public void teleportToOldLocation() {
        getEntity().teleport(previousLocations.get(getEntity().getEntityId()));
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
