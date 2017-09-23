package me.trolking1.calorthrealmcore;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 3/29/2017.
 */
public class ConfigManager {

    private static Config config, messages, guildBank, customitems, guild, classes, abilities, accountSelector, classSelector, dungeons;
    private List<Config> guilds = new ArrayList<>();

    public ConfigManager(Plugin plugin) {
        config = new Config(plugin, "config", true);
        messages = new Config(plugin, "messages", true);
        guildBank = new Config(plugin, "guildbank", true);
        customitems = new Config(plugin, "customitems", true);
        guild = new Config(plugin, "guild", true);
        abilities = new Config(plugin, "abilities", true);
        classes = new Config(plugin, "classes", true);
        accountSelector = new Config(plugin, "accountselector", true);
        classSelector = new Config(plugin, "classselector", true);
        dungeons = new Config(plugin, "dungeons", false);

        manageGuilds(plugin);
    }

    public Config getConfig() {
        return config;
    }

    public Config getMessages() {
        return messages;
    }

    public  Config getGuildBank() { return guildBank; }

    public Config getCustomitems() {
        return customitems;
    }

    public Config getGuild() {
        return guild;
    }

    public Config getClasses() {
        return classes;
    }

    public Config getAbilities() {
        return abilities;
    }

    public Config getAccountSelector() {
        return accountSelector;
    }

    public Config getClassSelector() {
        return classSelector;
    }

    public Config getDungeons() {
        return dungeons;
    }

    public Config getGuildsFile(String name) {
        for (Config guildFile : guilds) {
            if (guildFile.getFileName().equals(name)) {
                return guildFile;
            }
        }

        return null;
    }

    public List<Config> getGuilds() {
        return guilds;
    }

    public void manageGuilds(Plugin plugin) {
        File guildFolder = new File(plugin.getDataFolder() + "/guilds");

        if (!guildFolder.exists()) {
            guildFolder.mkdirs();
        }

        if (guildFolder != null) {
            for (File file : guildFolder.listFiles()) {
                guilds.add(new Config(plugin, "/guilds/" + file.getName().replace(".yml", ""), false));
            }
        }
    }
}
