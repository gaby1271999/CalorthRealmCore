package me.trolking1.calorthrealmcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class SpawnParticle {

    public static void spawnParticle(Player player, Particle particle, double x, double y, double z, int count) {
        //PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(id), distance, x, y, z, offsetX, offsetY, offsetZ, speed, amount, null);

        player.getWorld().spawnParticle(particle, x, y, z, count);
    }
}
