package com.karahanbuhan.finaltick;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public Configuration(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public long getTarget() {
        return config.getLong("target");
    }

    public Map<Integer, String[]> getTriggers() {
        Map<Integer, String[]> triggers = new HashMap<>();
        ConfigurationSection triggersSection = config.getConfigurationSection("triggers");

        if (triggersSection != null) {
            for (String key : triggersSection.getKeys(false)) {
                int seconds = (int) Math.min(Integer.MAX_VALUE, Math.max(Integer.MIN_VALUE, Long.parseLong(key)));
                List<String> commands = triggersSection.getStringList(key);
                triggers.put(seconds, commands.toArray(new String[0]));
            }
        }

        return triggers;
    }
}