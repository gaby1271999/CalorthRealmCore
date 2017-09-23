package me.trolking1.calorthrealmcore.playerinfo.classes;

import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;

import java.util.List;

/**
 * Created by Gabriel on 4/12/2017.
 */
public class Rogue extends PlayerClass {

    public Rogue(int damagePerLevel, int rangePerLevel, int defencePerLevel, int attackSpeedPerLevel, boolean premium, Item profileItem, Item mainItem, List<Item> items, List<String> abilitys) {
        super(damagePerLevel, rangePerLevel, defencePerLevel, attackSpeedPerLevel, premium, profileItem, mainItem, items, null);
    }
}