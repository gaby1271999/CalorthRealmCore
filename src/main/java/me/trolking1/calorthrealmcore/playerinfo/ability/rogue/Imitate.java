package me.trolking1.calorthrealmcore.playerinfo.ability.rogue;

import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Map;

/**
 * Created by Gabriel on 4/11/2017.
 */
@SerializableAs("imitate")
public class Imitate extends Ability implements ConfigurationSerializable {

    public Imitate(byte tier, int minLevel, double attackDamage, double rangeDamage, double defence, double attackSpeed, String spell, byte hunger) {
        super(tier, minLevel, attackDamage, rangeDamage, defence, attackSpeed, spell);
    }

    public Imitate(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        return map;
    }
}
