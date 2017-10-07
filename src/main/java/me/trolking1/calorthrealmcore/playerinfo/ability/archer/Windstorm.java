package me.trolking1.calorthrealmcore.playerinfo.ability.archer;

import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import me.trolking1.calorthrealmcore.playerinfo.ability.FireEffect;
import me.trolking1.calorthrealmcore.playerinfo.classes.Archer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 4/10/2017.
 */
@SerializableAs("windstorm")
public class Windstorm extends Ability implements ConfigurationSerializable {

    private byte hunger, duration;
    private List<PotionEffect> effects;
    private FireEffect fireEffect;

    public Windstorm(byte tier, int minLevel, double attackDamage, double rangeDamage, double defence, double attackSpeed, String spell, byte hunger, byte duration, FireEffect fireEffect, List<PotionEffect> effects) {
        super(tier, minLevel, attackDamage, rangeDamage, defence, attackSpeed, spell);

        this.hunger = hunger;
        this.duration = duration;
        this.fireEffect = fireEffect;
        this.effects = effects;
    }

    public Windstorm(Map<String, Object> map) {
        super(map);

        this.hunger = Main.intToByte((int) map.get("hunger"));
        this.duration =  Main.intToByte((int) map.get("duration"));
        this.fireEffect = (FireEffect) map.get("fireeffect");
        this.effects = (List<PotionEffect>) map.get("effects");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();

        map.put("hunger", hunger);
        map.put("duration", duration);
        map.put("fireeffect", fireEffect);
        map.put("effects", effects);

        return map;
    }

    public void startAbility(PlayerData playerData) {
        Archer archer = (Archer) playerData.getPlayerClass();
        archer.suptractHunger(hunger);
        archer.increasePercentageToOptions(getAttackDamage(), getRangeDamage(), getDefence(), getAttackSpeed());

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.main, new Runnable() {
            @Override
            public void run() {
                archer.setupClassOptions(playerData);
            }
        }, 20*duration);
    }

    public void setPlayerHitEffect(Player player) {
        if (fireEffect != null) {
            player.setFireTicks(20*this.fireEffect.getDuration());
        }

        player.addPotionEffects(this.effects);
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

    public FireEffect getFireEffect() {
        return fireEffect;
    }

    public void setFireEffect(FireEffect fireEffect) {
        this.fireEffect = fireEffect;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<PotionEffect> effects) {
        this.effects = effects;
    }
}
