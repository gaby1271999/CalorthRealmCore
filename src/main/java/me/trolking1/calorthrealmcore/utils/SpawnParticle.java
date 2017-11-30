package me.trolking1.calorthrealmcore.utils;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SpawnParticle {

    public static void spawnParticle(EnumParticle particle, double x, double y, double z, int count) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, false, (float) x, (float) y, (float) z, (float) 0, (float) 0, (float) 0, (float) 0, count);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
