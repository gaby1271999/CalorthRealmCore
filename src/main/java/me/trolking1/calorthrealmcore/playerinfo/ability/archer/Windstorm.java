package me.trolking1.calorthrealmcore.playerinfo.ability.archer;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.events.BowEvents;
import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import me.trolking1.calorthrealmcore.playerinfo.ability.Ability;
import me.trolking1.calorthrealmcore.playerinfo.ability.FireEffect;
import me.trolking1.calorthrealmcore.playerinfo.classes.Archer;
import me.trolking1.calorthrealmcore.utils.SpawnParticle;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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

        this.hunger = Main.getMain().intToByte((int) map.get("hunger"));
        this.duration =  Main.getMain().intToByte((int) map.get("duration"));
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

    public int startAbility(Player player, PlayerData playerData) {
        Archer archer = (Archer) playerData.getPlayerClass();
        if (archer.getHunger()-hunger >= 0) {
            archer.suptractHunger(hunger);
            archer.increasePercentageToOptions(getAttackDamage(), getRangeDamage(), getDefence(), getAttackSpeed());

            int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () -> {
                for (int i = 0; i < 7; i++) {
                    double alpha = 0;
                    if (player.getLocation().getDirection().getZ() == 1) {
                        alpha = Math.PI/2;
                    } else if (player.getLocation().getDirection().getZ() == -1) {
                        alpha = (3*Math.PI)/2;
                    } else {
                        alpha = Math.atan(player.getLocation().getDirection().getZ()/player.getLocation().getDirection().getX());
                    }

                    double start = alpha - (Math.PI/12);

                    double total = start + Math.toRadians(i*5);

                    double hypotenuse = player.getLocation().getDirection().getX()/Math.abs(Math.cos(alpha));

                    double x = hypotenuse*Math.cos(total);
                    double y = player.getLocation().getDirection().getY();
                    double z = hypotenuse*Math.sin(total);

                    Arrow arrow = player.launchProjectile(Arrow.class);
                    arrow.setVelocity(new Vector(x, y, z).multiply(2));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SpawnParticle.spawnParticle(EnumParticle.VILLAGER_HAPPY, arrow.getLocation().getX(), arrow.getLocation().getY(), arrow.getLocation().getZ(), 1);
                            if (arrow.isOnGround() || (!arrow.isValid())) {
                                arrow.remove();
                                cancel();
                            }
                        }
                    }.runTaskTimer(Main.getMain(), 1, 2);

                }
            }, 0, 10);

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), () -> {
                archer.setupClassOptions(playerData);
                Bukkit.getScheduler().cancelTask(task);
            }, 20 * duration);
            return 1;
        } else {
            return 2;
        }
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
