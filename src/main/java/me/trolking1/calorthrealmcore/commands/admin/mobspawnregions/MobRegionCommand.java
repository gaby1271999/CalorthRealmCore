package me.trolking1.calorthrealmcore.commands.admin.mobspawnregions;

import me.trolking1.calorthrealmcore.commands.CommandInterface;
import me.trolking1.calorthrealmcore.custommobs.CustomZombie;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;



public class MobRegionCommand extends CommandInterface {

    public MobRegionCommand() {
        super(null, "admin.spawnmob.description", "admin", "a");

        Map<String, Integer> args = new HashMap<>();
        args.put("spawnmob", 0);
        args.put("sm", 0);
        setArgs(args);
        setUsage("admin", "spawnmob");
    }

    @Override
    public boolean excecute(Player player, String[] args) {

        if (args.length == 1) {
            CustomZombie e = new CustomZombie(player.getWorld());
            e.setLocation(player.getLocation().getBlockX(), player.getLocation().getY(), player.getLocation().getBlockZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
            ((CraftWorld) player.getWorld()).getHandle().addEntity(e);

            Entity mob = e.getBukkitEntity();

            System.out.println("spawned");
            return true;
        }
        return false;
    }
}
