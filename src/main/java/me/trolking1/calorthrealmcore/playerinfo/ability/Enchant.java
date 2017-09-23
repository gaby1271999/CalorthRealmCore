package me.trolking1.calorthrealmcore.playerinfo.ability;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 4/11/2017.
 */
@SerializableAs("enchants")
public class Enchant implements ConfigurationSerializable {

    private int enchant;
    private byte lvl;

    public Enchant(int enchant, byte lvl) {
        this.enchant = enchant;
        this.lvl = lvl;
    }

    public Enchant(Map<String, Object> map) {
        this.enchant = (int) map.get("enchant");
        this.lvl = (byte) map.get("lvl");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("enchant", enchant);
        map.put("lvl", lvl);

        return map;
    }

    public int getEnchant() {
        return enchant;
    }

    public void setEnchant(int enchant) {
        this.enchant = enchant;
    }

    public byte getLvl() {
        return lvl;
    }

    public void setLvl(byte lvl) {
        this.lvl = lvl;
    }
}
