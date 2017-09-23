package me.trolking1.calorthrealmcore.customitems;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 5/2/2017.
 */
@SerializableAs("bow")
public class Bow extends CustomItem implements ConfigurationSerializable {

    private String arrowType;
    private int multiply, attackDamage, attackSpeed;

    public Bow(int attackDamage, int attackSpeed, String arrowType, int multiply, int id, short damage, List<ItemFlag> itemFlags, String name, List<String> lore, List<String> enchants) {
        super(id, damage, itemFlags, name, lore, enchants);

        this.arrowType = arrowType;
        this.multiply = multiply;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    public Bow(Map<String, Object> map) {
        super(map);

        this.arrowType = (String) map.get("arrowtype");
        this.multiply = (int) map.get("multiply");
        this.attackDamage = (int) map.get("attackdamage");
        this.attackSpeed = (int) map.get("attackspeed");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        map.put("arrowtype", arrowType);
        map.put("multiply", multiply);
        map.put("attackdamage", attackDamage);
        map.put("attackspeed", attackSpeed);

        return map;
    }

    public void shootArrow(Player player) {
        Projectile projectile;

        if (arrowType.equals("Arrow")) {
            projectile = player.launchProjectile(Arrow.class);
            projectile.setVelocity(player.getVelocity().multiply(multiply));
        }
    }

    public String getArrowType() {
        return arrowType;
    }

    public void setArrowType(String arrowType) {
        this.arrowType = arrowType;
    }

    public int getMultiply() {
        return multiply;
    }

    public void setMultiply(int multiply) {
        this.multiply = multiply;
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
