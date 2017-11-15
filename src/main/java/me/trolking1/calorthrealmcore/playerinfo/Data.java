package me.trolking1.calorthrealmcore.playerinfo;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Gabriel on 7/3/2017.
 */
public class Data {

    private Location location;
    private ItemStack[] items;
    private double health;

    public Data(Location location, ItemStack[] items, double health) {
        this.location = location;
        this.items = items;
        this.health = health;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
