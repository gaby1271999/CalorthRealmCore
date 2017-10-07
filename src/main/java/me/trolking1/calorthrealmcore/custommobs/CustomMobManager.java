package me.trolking1.calorthrealmcore.custommobs;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.*;

public class CustomMobManager {

    private Map<Integer, List<Entity>> customMobs = new HashMap<>();
    private List<MobSpawn> mobSpawns;

    public CustomMobManager() {
        if (Main.configManager.getMobSpawns().getConfig().get("spawns") != null) {
            this.mobSpawns = (List<MobSpawn>) Main.configManager.getMobSpawns().getConfig().get("spawns");

            spawnMobs();
        }
    }

    private void spawnMobs() {
        for (MobSpawn mobSpawn : mobSpawns) {
            List<Entity> entities = new ArrayList<>();

            for (int i = 0; i < mobSpawn.getAmount(); i++) {
                entities.add(mobSpawn.spawnMob());
            }

            customMobs.put(mobSpawn.getId(), entities);
        }
    }

    public boolean containsEntity(Entity entity) {
        for (Integer region : customMobs.keySet()) {
            for (Entity spawnedEntity : customMobs.get(region)) {
                if (spawnedEntity.getEntityId() == entity.getEntityId()) {
                    return true;
                }
            }
        }

        return false;
    }

    public MobSpawn getMobSpawn(int regionId) {
        if (mobSpawns != null) {
            for (MobSpawn mobSpawn : mobSpawns) {
                if (mobSpawn.getId() == regionId) {
                    return mobSpawn;
                }
            }
        }

        return null;
    }

    public boolean checkEntityInRegionLocation(Entity entity) {
        int currentY = entity.getLocation().getBlockY();
        int maxDepth = Main.configManager.getConfig().getConfig().getInt("custommob.maxdepth");

        for (int y = currentY ; y >= maxDepth; y--) {
            Block block = entity.getWorld().getBlockAt(entity.getLocation().getBlockX(), y, entity.getLocation().getBlockZ());
            if (block.getType() == Material.getMaterial(Main.configManager.getConfig().getConfig().getInt("custommob.blockid"))) {
                return true;
            }
        }

        removeMob(entity);
        return false;
    }

    private void removeMob(Entity checkEntity) {
        for (int regionId : customMobs.keySet()) {
            List<Entity> entities = customMobs.get(regionId);

            for (Entity entity : customMobs.get(regionId)) {
                if (entity.getEntityId() == checkEntity.getEntityId()) {
                    entities.remove(checkEntity);
                    customMobs.put(regionId, entities);
                    spawnMob(regionId);
                    break;
                }
            }
        }
    }

    private void spawnMob(int regionId) {
        for (MobSpawn mobSpawn : mobSpawns) {
            if (mobSpawn.getId() == regionId) {
                customMobs.get(regionId).add(mobSpawn.spawnMob());
                break;
            }
        }
    }

    public boolean maxMobs(int regionId) {
        if (getMobSpawn(regionId).getAmount() == customMobs.get(regionId).size()) {
            return true;
        }

        return false;
    }
}
