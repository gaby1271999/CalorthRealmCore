package me.trolking1.calorthrealmcore.custommobs;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@SerializableAs("mobspawn")
public class MobSpawn implements ConfigurationSerializable {

    private int id;
    private List<Location> locations;
    private int customMobId;
    private byte amount;

    public MobSpawn(int id, List<Location> locations, int customMobId, byte amount) {
        this.id = id;
        this.locations = locations;
        this.customMobId = customMobId;
        this.amount = amount;
    }

    public MobSpawn(Map<String, Object> map) {
        this.id = (int) map.get("id");
        this.locations = (List<Location>) map.get("locations");
        this.customMobId = (int) map.get("custommobid");
        this.amount = Main.intToByte((int) map.get("amount"));
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("locations", locations);
        map.put("custommobid", customMobId);
        map.put("amount", amount);

        return map;
    }

    public Entity spawnMob() {
        if (!Main.getCustomMobManager().maxMobs(id)) {
            int random = new Random().nextInt(locations.size());

            return Main.getCustomMobManager().getCustomMobe(customMobId).createMob(locations.get(random));
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public int getCustomMobId() {
        return customMobId;
    }

    public void setCustomMobId(int customMobId) {
        this.customMobId = customMobId;
    }

    public byte getAmount() {
        return amount;
    }

    public void setAmount(byte amount) {
        this.amount = amount;
    }
}
