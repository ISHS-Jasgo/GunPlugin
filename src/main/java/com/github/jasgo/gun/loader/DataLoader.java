package com.github.jasgo.gun.manager;

import com.github.jasgo.gun.Gun;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataManager {

    private final Gun plugin;
    private FileConfiguration[] config;

    public DataManager(Gun plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration load(String name) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name));
        InputStream defaultStream = plugin.getResource(name);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
        return config;
    }

    public void loadAll() {
        File[] files = plugin.getDataFolder().listFiles();
        if (files == null) return;
        FileConfiguration[] configs = new FileConfiguration[files.length];
        for (int i = 0; i < files.length; i++) {
            configs[i] = load(files[i].getName());
        }
        setConfig(configs);
    }

    public FileConfiguration[] getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration[] config) {
        this.config = config;
    }
}
