package me.trolking1.calorthrealmcore.playerinfo.classes;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import me.trolking1.calorthrealmcore.playerinfo.ability.FireEffect;
import me.trolking1.calorthrealmcore.playerinfo.ability.archer.Windstorm;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 4/12/2017.
 */
@SerializableAs("archer")
public class Archer extends PlayerClass implements ConfigurationSerializable {

    private Projectile projectile;
    private int damage, range, defence, attackSpeed, beginHealth, hungerFeedTimer;
    private byte hunger, beginHunger;
    private Ability currentAbility = null;

    public Archer(int damagePerLevel, int rangePerLevel, int defencePerLevel, int attackSpeedPerLevel, boolean premium, int hungerFeedTimer, byte beginHunger, Item profileItem, int beginHealth, Item mainItem, List<Item> items, List<String> abilitys) {
        super(damagePerLevel, rangePerLevel, defencePerLevel, attackSpeedPerLevel, premium, profileItem, beginHealth, mainItem, items, null);

        this.hungerFeedTimer = hungerFeedTimer;
        this.beginHunger = beginHunger;
        this.hunger = beginHunger;
        this.beginHealth = beginHealth;

        checkHunger();
    }

    public Archer(Map<String, Object> map) {
        super(map);

        this.hungerFeedTimer = (int) map.get("hungerfeedtimer");
        this.beginHunger = Main.intToByte((int) map.get("beginhunger"));
        this.hunger = beginHunger;
        this.beginHealth = (int) map.get("beginhealth");

        checkHunger();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        return map;
    }

    public void setupClassOptions(PlayerData playerData) {
        this.damage = playerData.getLevelUp().getLevel()*super.getDamagePerLevel();
        this.range = playerData.getLevelUp().getLevel()*super.getRangePerLevel();
        this.defence = playerData.getLevelUp().getLevel()*super.getDefencePerLevel();
        this.attackSpeed = playerData.getLevelUp().getLevel()*super.getAttackSpeedPerLevel();
    }

    public void increasePercentageToOptions(double attackDamage, double rangeDamage, double defence, double attackSpeed) {
        this.damage = (int) (this.damage*attackDamage);
        this.range = (int) (this.range*rangeDamage);
        this.defence = (int) (this.defence*defence);
        this.attackSpeed = (int) (this.attackSpeed*attackSpeed);
    }

    public void activateAbilities(Player player, PlayerData playerData, String name) {
        Ability ability = null;
        int previousLevel = 0;
        for (Object object : super.getAbilities()) {
            if (name.equals("Windstorm") && object.getClass().getName().equals(name)) {
                if (((Ability) object).getMinLevel() <= playerData.getLevelUp().getLevel()) {
                    if (previousLevel < ((Ability) object).getMinLevel()) {
                        ability = (Windstorm) object;
                        previousLevel = ability.getMinLevel();
                    }
                }
            }
        }

        if (name.equals("Windstorm")) {
            Windstorm windstorm = (Windstorm) ability;
            windstorm.startAbility(player, playerData);
            currentAbility = windstorm;
        }
    }

    public void suptractHunger(int hunger) {
        this.hunger -= hunger;
    }

    public byte getBeginHunger() {
        return beginHunger;
    }

    public byte getHunger() {
        return hunger;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Ability getCurrentAbility() {
        return currentAbility;
    }

    private void checkHunger() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getMain(), () -> {
            if (hunger < beginHunger) {
                hunger+=1;
            }
        }, 0, hungerFeedTimer);
    }
}
