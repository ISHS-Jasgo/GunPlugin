package com.github.jasgo.gun.loader;

import com.github.jasgo.gun.Gun;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataLoader {

    private FileConfiguration[] config;

    public DataLoader() {
    }

    public FileConfiguration load(String name) {
        Gun plugin = Gun.getInstance();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "/gunInfo/" + name));
        InputStream defaultStream = plugin.getResource("/gunInfo/" + name);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
        return config;
    }

    public void loadAll() {
        Gun plugin = Gun.getInstance();
        File dir = new File(plugin.getDataFolder(), "/gunInfo/");
        File[] files = dir.listFiles();
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
