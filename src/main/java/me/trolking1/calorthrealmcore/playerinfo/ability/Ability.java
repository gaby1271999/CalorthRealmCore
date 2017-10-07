package me.trolking1.calorthrealmcore.playerinfo.ability;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 4/10/2017.
 */
public class Ability {

    private byte tier;
    private int minLevel;
    private double attackDamage, rangeDamage, defence, attackSpeed;
    private String spell;

    public Ability(byte tier, int minLevel, double attackDamage, double rangeDamage, double defence, double attackSpeed, String spell) {
        this.tier = tier;
        this.minLevel = minLevel;
        this.attackDamage = attackDamage;
        this.rangeDamage = rangeDamage;
        this.defence = defence;
        this.attackSpeed = attackSpeed;
        this.spell = spell;
    }

    public Ability(Map<String, Object> map) {
        this.tier = Main.intToByte((int) map.get("tier"));
        this.minLevel = (int) map.get("minlevel");
        this.attackDamage = (double) map.get("attackdamage");
        this.rangeDamage = (double) map.get("rangedamage");
        this.defence = (double) map.get("defence");
        this.attackSpeed = (double) map.get("attackspeed");
        this.spell = (String) map.get("spell");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("tier", tier);
        map.put("minlevel", minLevel);
        map.put("attackdamage", attackDamage);
        map.put("rangedamage", rangeDamage);
        map.put("defence", defence);
        map.put("attackspeed", attackSpeed);
        map.put("spell", spell);

        return map;
    }

    public byte getTier() {
        return tier;
    }

    public void setTier(byte tier) {
        this.tier = tier;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public double getRangeDamage() {
        return rangeDamage;
    }

    public void setRangeDamage(double rangeDamage) {
        this.rangeDamage = rangeDamage;
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }
}
