package me.trolking1.calorthrealmcore.custommobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("custommob")
public class CustomMob implements ConfigurationSerializable {

    private String displayName;
    private int id, minLvl, maxLvl, health;
    private double damagePerLevel;
    private EntityType entityType;

    public CustomMob(int id, String displayName, int health, int minLvl, int maxLvl, double damagePerLevel, EntityType entityType) {
        this.id = id;
        this.displayName = displayName;
        this.health = health;
        this.minLvl = minLvl;
        this.maxLvl = maxLvl;
        this.damagePerLevel = damagePerLevel;
        this.entityType = entityType;
    }

    public CustomMob(Map<String, Object> map) {
        this.id = (int) map.get("id");
        this.displayName = (String) map.get("displayname");
        this.health = (int) map.get("health");
        this.minLvl = (int) map.get("minlvl");
        this.maxLvl = (int) map.get("maxlvl");
        this.damagePerLevel = (double) map.get("damageperlvl");
        this.entityType = EntityType.valueOf((String) map.get("entitytype"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("displayname", displayName);
        map.put("health", health);
        map.put("minlvl", minLvl);
        map.put("maxlvl", maxLvl);
        map.put("damageperlvl", damagePerLevel);
        map.put("entitytype", entityType);

        return map;
    }

    public Entity createMob(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, entityType);
        entity.setCustomNameVisible(true);
        entity.setCustomName(ChatColor.translateAlternateColorCodes('&', displayName));

        if (entity.getType() == EntityType.ZOMBIE) {
            Zombie zombie = (Zombie) entity;

            zombie.setBaby(false);
        }


        return entity;
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }
}
