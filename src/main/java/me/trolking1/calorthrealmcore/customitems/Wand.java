package me.trolking1.calorthrealmcore.customitems;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 5/2/2017.
 */
@SerializableAs("wand")
public class Wand extends CustomItem implements ConfigurationSerializable {

    private int attackDamage, attackSpeed;

    public Wand(int attackDamage, int attackSpeed, int id, short damage, List<ItemFlag> itemFlags, String name, List<String> lore, List<String> enchants) {
        super(id, damage, itemFlags, name, lore, enchants);

        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    public Wand(Map<String, Object> map) {
        super(map);

        this.attackDamage = (int) map.get("attackdamage");
        this.attackSpeed = (int) map.get("attackspeed");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        map.put("attackdamage", attackDamage);
        map.put("attackspeed", attackSpeed);

        return map;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }
}
