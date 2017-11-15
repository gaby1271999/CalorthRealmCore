package me.trolking1.calorthrealmcore.playerinfo.classes;

import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 3/31/2017.
 */
public class Paladin extends PlayerClass implements ConfigurationSerializable {

    public Paladin(int damagePerLevel, int rangePerLevel, int defencePerLevel, int attackSpeedPerLevel, boolean premium, Item profileItem, int beginHealth, Item mainItem, List<Item> items, List<String> abilitys) {
        super(damagePerLevel, rangePerLevel, defencePerLevel, attackSpeedPerLevel, premium, profileItem, beginHealth, mainItem, items, null);
    }

    public Paladin(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        return map;
    }
}
