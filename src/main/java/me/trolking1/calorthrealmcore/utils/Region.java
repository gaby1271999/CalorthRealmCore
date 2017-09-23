package me.trolking1.calorthrealmcore.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 3/30/2017.
 */
public class Region {

    private Location location1, location2;

    public Region(Location location1, Location location2) {
        this.location1 = location1;
        this.location2 = location2;
    }

    public Location getLocation1() {
        return location1;
    }

    public void setLocation1(Location location1) {
        this.location1 = location1;
    }

    public Location getLocation2() {
        return location2;
    }

    public void setLocation2(Location location2) {
        this.location2 = location2;
    }

    public boolean inRegion(Location location) {
        if (location.getWorld().getName().equals(location1.getWorld().getName())) {
            int minX = Math.min(location1.getBlockX(), location2.getBlockX());
            int maxX = Math.max(location1.getBlockX(), location2.getBlockX());
            int minY = Math.min(location1.getBlockY(), location2.getBlockY());
            int maxY = Math.max(location1.getBlockY(), location2.getBlockY());
            int minZ = Math.min(location1.getBlockZ(), location2.getBlockZ());
            int maxZ = Math.max(location1.getBlockZ(), location2.getBlockZ());

            if (minX <= location.getBlockX() && maxX >= location.getBlockX()) {
                if (minY <= location.getBlockY() && maxY >= location.getBlockY()) {
                    if (minZ <= location.getBlockZ() && maxZ >= location.getBlockZ()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public List<Block> getRegionBlocks() {
        List<Block> blocks = new ArrayList<>();

        int minX = Math.min(location1.getBlockX(), location2.getBlockX());
        int maxX = Math.max(location1.getBlockX(), location2.getBlockX());
        int minY = Math.min(location1.getBlockY(), location2.getBlockY());
        int maxY = Math.max(location1.getBlockY(), location2.getBlockY());
        int minZ = Math.min(location1.getBlockZ(), location2.getBlockZ());
        int maxZ = Math.max(location1.getBlockZ(), location2.getBlockZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    blocks.add(location1.getWorld().getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }
}
