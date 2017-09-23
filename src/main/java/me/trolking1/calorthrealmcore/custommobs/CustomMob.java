package me.trolking1.calorthrealmcore.custommobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("custommob")
public class CustomMob implements ConfigurationSerializable {

    private String displayName;
    private int minLvl, maxLvl, damagePerLevel;
    private Location location;
    private EntityType entityType;

    public CustomMob(String displayName, int minLvl, int maxLvl, int damagePerLevel, Location location, EntityType entityType) {
        this.displayName = displayName;
        this.minLvl = minLvl;
        this.maxLvl = maxLvl;
        this.damagePerLevel = damagePerLevel;
        this.location = location;
        this.entityType = entityType;
    }

    public CustomMob(Map<String, Object> map) {
        this.displayName = (String) map.get("displayname");
        this.minLvl = (int) map.get("minlvl");
        this.maxLvl = (int) map.get("maxlvl");
        this.damagePerLevel = (int) map.get("damageperlvl");
        this.location = (Location) map.get("location");
        this.entityType = (EntityType) map.get("entitytype");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
