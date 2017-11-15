package me.trolking1.calorthrealmcore.custommobs;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.*;

public class CustomMobManager {

    private Map<Integer, List<Entity>> customMobs = new HashMap<>();
    private List<MobSpawn> mobSpawns;

    public void setupMobSpawns() {
        if (Main.getConfigManager().getMobSpawns().getConfig().get("spawns") != null) {
            this.mobSpawns = (List<MobSpawn>) Main.getConfigManager().getMobSpawns().getConfig().get("spawns");

            spawnMobs();
        }
    }

    public void shutDown() {
        for (int id : customMobs.keySet()) {
            List<Entity> entities = customMobs.get(id);

            if (!entities.isEmpty()) {
                for (Entity entity : entities) {
                    entity.remove();
                }
            }
        }
    }

    private void spawnMobs() {
        for (MobSpawn mobSpawn : mobSpawns) {
            if (mobSpawn.getLocations() != null) {
                List<Entity> entities = new ArrayList<>();

                for (int i = 0; i < mobSpawn.getAmount(); i++) {
                    entities.add(mobSpawn.spawnMob());
                }

                customMobs.put(mobSpawn.getId(), entities);
            }
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

    public CustomMob getCustomMob(Entity entity) {
        for (Integer region : customMobs.keySet()) {
            for (Entity spawnedEntity : customMobs.get(region)) {
                if (spawnedEntity.getEntityId() == entity.getEntityId()) {
                    return getCustomMobe(getMobSpawn(region).getCustomMobId());
                }
            }
        }

        return null;
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
        int maxDepth = Main.getConfigManager().getConfig().getConfig().getInt("custommob.maxdepth");

        for (int y = currentY ; y >= maxDepth; y--) {
            Block block = entity.getWorld().getBlockAt(entity.getLocation().getBlockX(), y, entity.getLocation().getBlockZ());
            if (block.getType() == Material.getMaterial(Main.getConfigManager().getConfig().getConfig().getInt("custommob.blockid"))) {
                return true;
            }
        }

        removeMob(entity);
        return false;
    }

    public void removeMob(Entity checkEntity) {
        for (int regionId : customMobs.keySet()) {
            List<Entity> entities = customMobs.get(regionId);

            for (Entity entity : customMobs.get(regionId)) {
                if (entity.getEntityId() == checkEntity.getEntityId()) {
                    entities.remove(checkEntity);
                    checkEntity.remove();
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
        if (!customMobs.isEmpty() && customMobs.get(regionId) != null) {
            if (getMobSpawn(regionId).getAmount() == customMobs.get(regionId).size()) {
                return true;
            }
        }

        return false;
    }

    public int deleteRegion(int id) {
        MobSpawn mobSpawn = getMobSpawn(id);

        if (mobSpawn != null) {
            List<MobSpawn> mobSpawns = (List<MobSpawn>) Main.getConfigManager().getMobSpawns().getConfig().get("spawns");
            mobSpawns.remove(mobSpawn);

            Main.getConfigManager().getMobSpawns().getConfig().set("spawns", mobSpawns);
            Main.getConfigManager().getMobSpawns().saveConfig();

            if (!customMobs.isEmpty()) {
                for (Entity entity : customMobs.get(id)) {
                    entity.remove();
                }
            }

            customMobs.remove(id);
            this.mobSpawns.remove(mobSpawn);

            return 1;
        } else {
            return 2;
        }
    }

    public int addSpawn(int id, Location location) {
        MobSpawn mobSpawn = getMobSpawn(id);

        if (mobSpawn != null) {
            List<Location> locations = mobSpawn.getLocations();
            if (locations == null) {
                locations = new ArrayList<Location>();
            }

            locations.add(location);

            mobSpawn.setLocations(locations);

            Main.getConfigManager().getMobSpawns().getConfig().set("spawns", mobSpawns);
            Main.getConfigManager().getMobSpawns().saveConfig();

            spawnMob(id);

            return 1;
        } else {
            return 2;
        }
    }

    public int removeSpawn(int id, Location location) {
        MobSpawn mobSpawn = getMobSpawn(id);

        if (mobSpawn != null) {
            if (containsLocation(mobSpawn, location) != null) {
                mobSpawn.getLocations().remove(containsLocation(mobSpawn, location));

                Main.getConfigManager().getMobSpawns().getConfig().set("spawns", mobSpawns);
                Main.getConfigManager().getMobSpawns().saveConfig();

                return 1;
            }

            return 3;
        } else {
            return 2;
        }
    }

    public int createMobSpawn(int customMobId, byte amount) {
        if (getCustomMobe(customMobId) != null) {
            int id = 1;
            if (mobSpawns != null) {
                id = mobSpawns.size() + 1;
            }

            MobSpawn mobSpawn = new MobSpawn(id, null, customMobId, amount);

            if (mobSpawns == null) {
                mobSpawns = new ArrayList<MobSpawn>();
            }

            mobSpawns.add(mobSpawn);

            Main.getConfigManager().getMobSpawns().getConfig().set("spawns", mobSpawns);
            Main.getConfigManager().getMobSpawns().saveConfig();

            return mobSpawn.getId();
        }

        return 0;
    }

    private Location containsLocation(MobSpawn mobSpawn, Location location) {
        if (mobSpawn.getLocations() != null) {
            for (Location loc : mobSpawn.getLocations()) {
                if (loc.getBlockX() == location.getBlockX() && loc.getBlockY() == location.getBlockY() && loc.getBlockZ() == location.getBlockZ()) {
                    return loc;
                }
            }
        }

        return null;
    }

    public CustomMob getCustomMobe(int customMobId) {
        List<CustomMob> customMobs = (List<CustomMob>) Main.getConfigManager().getCustomMobs().getConfig().get("mobs");

        for (CustomMob customMob : customMobs) {
            if (customMob.getId() == customMobId) {
                return customMob;
            }
        }

        return null;
    }
}
