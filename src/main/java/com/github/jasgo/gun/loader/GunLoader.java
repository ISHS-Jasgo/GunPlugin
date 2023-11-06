package com.github.jasgo.gun.loader;

import com.github.jasgo.gun.Gun;
import com.github.jasgo.gun.manager.GunDataManager;
import com.github.jasgo.gun.stat.GunItem;
import com.github.jasgo.gun.stat.GunStat;
import com.github.jasgo.gun.stat.Penetration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GunLoader {

    private final DataLoader dataLoader;

    private final List<GunStat> gunStats = new ArrayList<>();

    private final List<GunItem> gunItems = new ArrayList<>();

    public GunLoader() {
        dataLoader = GunDataManager.dataLoader;
    }

    public void loadAll() {
        for (FileConfiguration config : dataLoader.getConfig()) {
            String name = config.getKeys(false).toArray()[0].toString();
            double damage = config.getDouble(name + ".damage");
            int magazine = config.getInt(name + ".magazine");
            double reload = config.getDouble(name + ".reload");
            int fireRate = config.getInt(name + ".fireRate");
            double bulletSpeed = config.getDouble(name + ".bulletSpeed");
            Penetration penetration = Penetration.valueOf(config.getString(name + ".penetration"));
            int bulletPerShot = config.getInt(name + ".bulletPerShot");
            GunStat gunStat = new GunStat(name, damage, magazine, reload, fireRate, bulletSpeed, penetration, bulletPerShot);
            addGunStat(gunStat);
        }
        loadAdaptedItems();
    }
    public void addGunStat(GunStat gunStat) {
        gunStats.add(gunStat);
    }

    public void saveAdaptedItems() {
        Gun plugin = Gun.getInstance();
        FileConfiguration config = plugin.getConfig();
        gunItems.forEach(gunItem -> {
            config.set(gunItem.getStat().getName(), gunItem.getItem());
        });
        try {
            config.save(plugin.getDataFolder() + "/config.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void adaptItem(ItemStack item, String name) {
        GunStat gunStat = getGunStat(name);
        GunItem gunItem = new GunItem(item, gunStat);
        if (containsGun(gunStat)) {
            gunItems.remove(getGunItem(gunStat));
        }
        gunItems.add(gunItem);
        saveAdaptedItems();
    }

    public GunItem getGunItem(GunStat gunStat) {
        GunItem result = null;
        for (GunItem gunItem : gunItems) {
            if (gunItem.getStat().equals(gunStat)) {
                result = gunItem;
                break;
            }
        }
        return result;
    }

    public boolean containsGun(GunStat gunStat) {
        boolean result = false;
        for (GunItem gunItem : gunItems) {
            if (gunItem.getStat().equals(gunStat)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void loadAdaptedItems() {
        Gun plugin = Gun.getInstance();
        FileConfiguration config = plugin.getConfig();
        for (String key : config.getKeys(false)) {
            gunItems.add(new GunItem(config.getItemStack(key), getGunStat(key)));
        }
    }

    public GunStat getGunStat(String name) {
        GunStat result = null;
        for (GunStat gunStat : gunStats) {
            if (gunStat.getName().equalsIgnoreCase(name)) {
                result = gunStat;
                break;
            }
        }
        return result;
    }

    public List<GunStat> getGunStats() {
        return gunStats;
    }

    public List<GunItem> getGunItems() {
        return gunItems;
    }
}
