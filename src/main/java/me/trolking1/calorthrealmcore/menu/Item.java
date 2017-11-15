package me.trolking1.calorthrealmcore.menu;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 6/28/2017.
 */

@SerializableAs("item")
public class Item implements ConfigurationSerializable {

    private int id, amount;
    private short damage;
    private byte data;
    private String name;
    private List<String> lore;
    private ItemStack item;

    public Item(Map<String, Object> map) {
        this.id = (int) map.get("id");
        this.amount = (int) map.get("amount");
        this.damage = Main.getMain().intToShort((int)map.get("damage"));
        this.data = Main.getMain().intToByte((int) map.get("data"));
        this.name = (String) map.get("name");
        this.lore = (List<String>) map.get("lore");

        item = new ItemStack(Material.getMaterial(id), amount, damage, data);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name != "" ? ChatColor.translateAlternateColorCodes('&', name) : null);
        List<String> newLore = new ArrayList<>();
        for (String line : lore) {
            newLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        itemMeta.setLore(newLore);
        item.setItemMeta(itemMeta);
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }

    public ItemStack getItem() {
        return item;
    }
}
