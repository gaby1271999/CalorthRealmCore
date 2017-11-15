package me.trolking1.calorthrealmcore.playerinfo.ability.archer;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import me.trolking1.calorthrealmcore.playerinfo.ability.Enchant;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 4/11/2017.
 */
@SerializableAs("rainoffire")
public class RainOfFire extends Ability implements ConfigurationSerializable {

    private byte use, arrows, hunger;
    private List<Enchant> enchants;
    private List<PotionEffect> effects;

    public RainOfFire(byte tier, int minLevel,  double attackDamage, double rangeDamage, double defence, double attackSpeed, String spell, byte hunger, byte use, byte arrows, List<PotionEffect> effects, List<Enchant> enchants) {
        super(tier, minLevel, attackDamage, rangeDamage, defence, attackSpeed, spell);

        this.hunger = hunger;
        this.use = use;
        this.arrows = arrows;
        this.effects = effects;
        this.enchants = enchants;
    }

    public RainOfFire(Map<String, Object> map) {
        super(map);

        this.hunger = Main.getMain().intToByte((int) map.get("hunger"));
        this.use = Main.getMain().intToByte((int) map.get("use"));
        this.arrows = Main.getMain().intToByte((int) map.get("arrows"));
        this.effects = (List<PotionEffect>) map.get("effects");
        this.enchants = (List<Enchant>) map.get("enchants");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        map.put("hunger", hunger);
        map.put("use", use);
        map.put("arrows", arrows);
        map.put("effects", effects);
        map.put("enchants", enchants);

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

    public List<Enchant> getEnchants() {
        return enchants;
    }

    public void setEnchants(List<Enchant> enchants) {
        this.enchants = enchants;
    }
}
