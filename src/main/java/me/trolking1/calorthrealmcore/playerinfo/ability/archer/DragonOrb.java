package me.trolking1.calorthrealmcore.playerinfo.ability.archer;

import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 4/11/2017.
 */
@SerializableAs("dragonorb")
public class DragonOrb extends Ability implements ConfigurationSerializable {

    private byte hunger, duration;
    private List<PotionEffect> effects;

    public DragonOrb(byte tier, int minLevel, double attackDamage, double rangeDamage, double defence, double attackSpeed, String spell, byte hunger, byte duration, List<PotionEffect> effects) {
        super(tier, minLevel, attackDamage, rangeDamage, defence, attackSpeed, spell);

        this.hunger = hunger;
        this.duration = duration;
        this.effects = effects;
    }

    public DragonOrb(Map<String, Object> map) {
        super(map);

        this.hunger = Main.intToByte((int) map.get("hunger"));
        this.duration = Main.intToByte((int) map.get("duration"));
        this.effects = (List<PotionEffect>) map.get("effects");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        map.put("hunger", this.hunger);
        map.put("duration", this.duration);
        map.put("effects", this.effects);

        return map;
    }

    public byte getHunger() {
        return hunger;
    }

    public void setHunger(byte hunger) {
        this.hunger = hunger;
    }

    public byte getDuration() {
        return duration;
    }

    public void setDuration(byte duration) {
        this.duration = duration;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<PotionEffect> effects) {
        this.effects = effects;
    }
}
