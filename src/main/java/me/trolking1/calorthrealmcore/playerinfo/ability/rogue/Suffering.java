package me.trolking1.calorthrealmcore.playerinfo.ability.rogue;

import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 4/11/2017.
 */
@SerializableAs("suffering")
public class Suffering extends Ability implements ConfigurationSerializable {

    private byte hunger, duration;
    private List<PotionEffect> effects;

    public Suffering(byte tier, int minLevel, double attackDamage, double rangeDamage, double defence, double attackSpeed, String spell, byte hunger, byte duration, List<PotionEffect> effects) {
        super(tier, minLevel, attackDamage, rangeDamage, defence, attackSpeed, spell);
    }

    public Suffering(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        return map;
    }
}
