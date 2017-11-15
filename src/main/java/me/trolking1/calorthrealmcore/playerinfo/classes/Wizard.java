package me.trolking1.calorthrealmcore.playerinfo.classes;

import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;

import java.util.List;

/**
 * Created by Gabriel on 4/13/2017.
 */
public class Wizard extends PlayerClass {

    public Wizard(int damagePerLevel, int rangePerLevel, int defencePerLevel, int attackSpeedPerLevel, boolean premium, Item profileItem, int beginHealth, Item mainItem, List<Item> items, List<String> abilitys) {
        super(damagePerLevel, rangePerLevel, defencePerLevel, attackSpeedPerLevel, premium, profileItem, beginHealth, mainItem, items, null);
    }
}
