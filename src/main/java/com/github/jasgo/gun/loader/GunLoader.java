package com.github.jasgo.gun.manager;

import com.github.jasgo.gun.Gun;
import com.github.jasgo.gun.stat.GunStat;
import com.github.jasgo.gun.stat.Penetration;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GunManager {

    private final Gun plugin;
    private final DataManager dataManager;

    private final List<GunStat> gunStats = new ArrayList<>();

    public GunManager(Gun plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.dataManager;
    }

    public void loadAll() {
        for (FileConfiguration config : dataManager.getConfig()) {
            String name = config.getKeys(false).toArray()[0].toString();
            double damage = config.getDouble(name + ".damage");
            int magazine = config.getInt(name + ".magazine");
            double reload = config.getDouble(name + ".reload");
            double fireRate = config.getDouble(name + ".fireRate");
            double bulletSpeed = config.getDouble(name + ".bulletSpeed");
            int bulletCount = config.getInt(name + ".bulletCount");
            Penetration penetration = Penetration.valueOf(config.getString(name + ".penetration"));
            int bulletPerShot = config.getInt(name + ".bulletPerShot");
            GunStat gunStat = new GunStat(name, damage, magazine, reload, fireRate, bulletSpeed, bulletCount, penetration, bulletPerShot);
            addGunStat(gunStat);
        }
    }
    public void addGunStat(GunStat gunStat) {
        gunStats.add(gunStat);
    }

    public List<GunStat> getGunStats() {
        return gunStats;
    }
}
