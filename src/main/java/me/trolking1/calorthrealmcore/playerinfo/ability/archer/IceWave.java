package me.trolking1.calorthrealmcore.playerinfo.ability.archer;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 4/11/2017.
 */
@SerializableAs("icewave")
public class IceWave extends Ability implements ConfigurationSerializable {

    private byte hunger, use, arrows;
    private List<PotionEffect> effects;

    public IceWave(byte tier, int minLevel, double attackDamage, double rangeDamage, double defence, double attackSpeed, String spell, byte hunger, byte use, byte arrows, List<PotionEffect> effects) {
        super(tier, minLevel, attackDamage, rangeDamage, defence, attackSpeed, spell);

        this.hunger = hunger;
        this.use = use;
        this.arrows = arrows;
        this.effects = effects;
    }

    public IceWave(Map<String, Object> map) {
        super(map);

        this.hunger = Main.getMain().intToByte((int) map.get("hunger"));
        this.use = Main.getMain().intToByte((int) map.get("use"));
        this.arrows = Main.getMain().intToByte((int) map.get("arrows"));
        this.effects = (List<PotionEffect>) map.get("effects");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        map.put("hunger", this.hunger);
        map.put("use", this.use);
        map.put("arrows", this.arrows);
        map.put("effects", this.effects);

        return map;
    }

    public byte getHunger() {
        return hunger;
    }

    public void setHunger(byte hunger) {
        this.hunger = hunger;
    }

    public byte getUse() {
        return use;
    }

    public void setUse(byte use) {
        this.use = use;
    }

    public byte getArrows() {
        return arrows;
    }

    public void setArrows(byte arrows) {
        this.arrows = arrows;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<PotionEffect> effects) {
        this.effects = effects;
    }
}
