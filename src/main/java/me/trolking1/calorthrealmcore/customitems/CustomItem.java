package me.trolking1.calorthrealmcore.customitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by Gabriel on 4/2/2017.
 */

public class CustomItem {

    private ItemStack item;

    public CustomItem(int id, short damage, List<ItemFlag> itemFlags, String name, List<String> lore, List<String> enchants) {
        ItemStack item = new ItemStack(Material.getMaterial(id), 1, (short) damage);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        for (ItemFlag itemFlag : itemFlags) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (String enchantAndValue : enchants) {
            String[] enchantAndValueSplit = enchantAndValue.split("-");
            itemMeta.addEnchant(Enchantment.getByName(enchantAndValueSplit[0]), Integer.valueOf(enchantAndValueSplit[1]), true);
        }
        item.setItemMeta(itemMeta);
        this.item = item;
    }

    public CustomItem(Map<String, Object> map) {
        ItemStack item = new ItemStack(Material.getMaterial((int) map.get("id")), 1, ((Integer) map.get("damage")).shortValue());
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        for (String itemFlag : (List<String>) map.get("itemflags")) {
            itemMeta.addItemFlags(ItemFlag.valueOf(itemFlag));
        }
        itemMeta.setDisplayName((String) map.get("name"));
        itemMeta.setLore((List<String>) map.get("lore"));
        for (String enchantAndValue : (List<String>) map.get("enchants")) {
            String[] enchantAndValueSplit = enchantAndValue.split("-");
            itemMeta.addEnchant(Enchantment.getByName(enchantAndValueSplit[0]), Integer.valueOf(enchantAndValueSplit[1]), true);
        }
        item.setItemMeta(itemMeta);
        this.item = item;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", item.getType().getId());
        map.put("damage", item.getDurability());
        List<String> itemFlags = new ArrayList<>();
        for (ItemFlag itemFlag : item.getItemMeta().getItemFlags()) {
            itemFlags.add(itemFlag.toString());
        }
        map.put("itemflags", itemFlags);
        map.put("name", item.getItemMeta().getDisplayName());
        map.put("lore", item.getItemMeta().getLore());
        List<String> enchants = new ArrayList<>();
        Iterator it = item.getItemMeta().getEnchants().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Enchantment enchantment = (Enchantment) pair.getKey();
            enchants.add(enchantment.getName() + "-" + pair.getValue());
        }
        map.put("enchants", enchants);

        return map;
    }

    public ItemStack getCustomItem() {
        return item;
    }


}
