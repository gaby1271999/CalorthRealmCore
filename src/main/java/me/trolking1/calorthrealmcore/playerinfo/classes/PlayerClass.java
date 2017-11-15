package me.trolking1.calorthrealmcore.playerinfo.classes;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import me.trolking1.calorthrealmcore.playerinfo.ability.archer.Windstorm;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 3/31/2017.
 */
public class PlayerClass {

    private int damagePerLevel, rangePerLevel, defencePerLevel, attackSpeedPerLevel, beginHealth;
    private boolean premium;
    private Item profileItem, mainItem;
    private List<Item> items;
    private List<Ability> abilities;

    public PlayerClass(int damagePerLevel, int rangePerLevel, int defencePerLevel, int attackSpeedPerLevel, boolean premium, Item profileItem, int beginHealth, Item mainItem, List<Item> items, List<Ability> abilities) {
        this.damagePerLevel = damagePerLevel;
        this.rangePerLevel = rangePerLevel;
        this.defencePerLevel = defencePerLevel;
        this.attackSpeedPerLevel = attackSpeedPerLevel;
        this.premium = premium;
        this.profileItem = profileItem;
        this.beginHealth = beginHealth;
        this.mainItem = mainItem;
        this.items = items;
        this.abilities = abilities;
    }

    public PlayerClass(Map<String, Object> map) {
        this.damagePerLevel = (int) map.get("damageperlevel");
        this.rangePerLevel = (int) map.get("rangeperlevel");
        this.defencePerLevel = (int) map.get("defenceperlevel");
        this.attackSpeedPerLevel = (int) map.get("attackspeedperlevel");
        this.premium = (boolean) map.get("premium");
        this.profileItem = (Item) map.get("profileitem");
        this.beginHealth = (int) map.get("beginhealth");
        this.mainItem = (Item) map.get("mainitem");
        this.items = (List<Item>) map.get("items");
        this.abilities = (List<Ability>) map.get("abilities");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("damageperlevel", damagePerLevel);
        map.put("rangeperlevel", rangePerLevel);
        map.put("defenceperlevel", defencePerLevel);
        map.put("attackspeedperlevel", attackSpeedPerLevel);
        map.put("premium", premium);
        map.put("profileitem", profileItem);
        map.put("beginhealth", beginHealth);
        map.put("mainitem", mainItem);
        map.put("items", items);
        map.put("abilities", abilities);

        return map;
    }

    public void activateAbility(Player player, String spell) {
        PlayerData playerData = Main.getPlayerInfoManager().getPlayerData(player);

        playerData.getLevelUp().setLevel(15);
        Ability abilityForLevel = null;
        for (Ability ability : abilities) {
            if (ability.getSpell().equals(spell)) {
                if (playerData.getLevelUp().getLevel() >= ability.getMinLevel()) {
                    abilityForLevel = ability;
                }
            }
        }

        if (abilityForLevel != null) {
            int state = 0;
            if (abilityForLevel.getClass().getSimpleName().equals("Windstorm")) {
                state = ((Windstorm) abilityForLevel).startAbility(player, playerData);
            }

            switch (state) {
                case 1:
                    Main.getMessageManager().sendMessage(player, Main.getMessageManager().getMessageFromConfig("ability.activated").replace("%ability%", abilityForLevel.getClass().getSimpleName()));
                    break;
                case 2:
                    Main.getMessageManager().sendMessage(player, Main.getMessageManager().getMessageFromConfig("ability.hunger").replace("%ability%", abilityForLevel.getClass().getSimpleName()));
                    break;
            }

        }
    }

    public int getDamagePerLevel() {
        return damagePerLevel;
    }

    public void setDamagePerLevel(int damagePerLevel) {
        this.damagePerLevel = damagePerLevel;
    }

    public int getRangePerLevel() {
        return rangePerLevel;
    }

    public void setRangePerLevel(int rangePerLevel) {
        this.rangePerLevel = rangePerLevel;
    }

    public int getDefencePerLevel() {
        return defencePerLevel;
    }

    public void setDefencePerLevel(int defencePerLevel) {
        this.defencePerLevel = defencePerLevel;
    }

    public int getAttackSpeedPerLevel() {
        return attackSpeedPerLevel;
    }

    public void setAttackSpeedPerLevel(int attackSpeedPerLevel) {
        this.attackSpeedPerLevel = attackSpeedPerLevel;
    }

    public boolean isPremium() {
        return premium;
    }

    public Item getProfileItem() {
        return profileItem;
    }

    public int getBeginHealth() {
        return beginHealth;
    }

    public void setBeginHealth(int beginHealth) {
        this.beginHealth = beginHealth;
    }

    public Item getMainItem() {
        return mainItem;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }
}
