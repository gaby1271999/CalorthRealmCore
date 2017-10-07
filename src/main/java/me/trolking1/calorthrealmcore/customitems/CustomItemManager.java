package me.trolking1.calorthrealmcore.customitems;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 4/2/2017.
 */
public class CustomItemManager {

    private Map<Integer, CustomItem> customItems = new HashMap<>();

    public void onStartUp() {
        for (String customItemId : Main.configManager.getCustomitems().getConfig().getConfigurationSection("items").getKeys(false)) {
            int id = Integer.valueOf(customItemId);
            CustomItem customItem = (CustomItem) Main.configManager.getCustomitems().getConfig().get("items." + customItemId);
            customItems.put(id, customItem);
        }
    }

    public Map<Integer, CustomItem> getCustomItems() {
        return customItems;
    }
}
