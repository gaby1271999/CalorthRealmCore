package me.trolking1.calorthrealmcore;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 4/2/2017.
 */
public class MessageManager {

    private FileConfiguration messageconfig = Main.configManager.getMessages().getConfig();
    private String prefix;

    public MessageManager() {
        prefix = Main.configManager.getMessages().getConfig().getString("prefix");
    }

    public void sendMessageFromConfig(Player player, String path) {
        player.sendMessage(toColor(prefix + messageconfig.getString(path)));
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(toColor(prefix + message));
    }

    public String getMessageFromConfig(String path) {
        return messageconfig.getString(path);
    }

    public void sendArrayMessageFromConfig(Player player, String path) {
        for (String message : messageconfig.getStringList(path)) {
            player.sendMessage(toColor(message));
        }
    }

    public String toColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
