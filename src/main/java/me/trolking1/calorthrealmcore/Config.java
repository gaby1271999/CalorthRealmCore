package me.trolking1.calorthrealmcore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Gabriel on 3/29/2017.
 */
public class Config {

    private String name;
    private File file;
    private FileConfiguration fileConfiguration;

    public Config(Plugin plugin, String name, boolean resourse) {
        this.name = name;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        file = new File(plugin.getDataFolder(), name + ".yml");

        if (resourse) {
            plugin.saveResource(name + ".yml", false);
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void saveConfig() {
        try {
            getConfig().save(file);
        } catch (IOException e) {
            System.out.println("Could not save " + this.name + ".yml");
        }
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public String getFileName() {
        return name;
    }

    public File getFile() {
        return file;
    }
}
