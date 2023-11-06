package com.github.jasgo.gun;

import com.github.jasgo.gun.listener.BulletHitListener;
import com.github.jasgo.gun.listener.GunShootListener;
import com.github.jasgo.gun.loader.DataLoader;
import com.github.jasgo.gun.loader.GunLoader;
import com.github.jasgo.gun.manager.GunDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Gun extends JavaPlugin {
    public static Gun getInstance() {
        return getPlugin(Gun.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        //create config folder
        File dir = new File(getDataFolder(), "gunInfo");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        saveDefaultConfig();
        GunDataManager.dataLoader.loadAll();
        GunDataManager.gunLoader.loadAll();

        getServer().getPluginManager().registerEvents(new GunShootListener(), this);
        getServer().getPluginManager().registerEvents(new BulletHitListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("gun")) {
            if (!sender.isOp()) return false;
            if (args.length == 0) {
                sender.sendMessage("Gun plugin by jasgo");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                GunDataManager.dataLoader.loadAll();
                GunDataManager.gunLoader.loadAll();
                sender.sendMessage("Reloaded all gun data");
                return true;
            }
            if (args[0].equalsIgnoreCase("adapt")) {
                if (args.length == 1) {
                    sender.sendMessage("Please specify a gun name");
                    return true;
                } else if (args.length == 2) {
                    Player player = (Player) sender;
                    GunDataManager.gunLoader.adaptItem(player.getInventory().getItemInMainHand(), args[1]);
                    sender.sendMessage("Adapted gun " + args[1] + "in " + player.getInventory().getItemInMainHand().getI18NDisplayName());
                }
            }
            if (args[0].equalsIgnoreCase("info")) {
                GunLoader gunLoader = GunDataManager.gunLoader;
                gunLoader.getGunStats().forEach(gunStat -> {
                    sender.sendMessage("====================================");
                    sender.sendMessage(gunStat.getName());
                    sender.sendMessage("Damage: " + gunStat.getDamage());
                    sender.sendMessage("Magazine: " + gunStat.getMagazine());
                    sender.sendMessage("Reload: " + gunStat.getReload());
                    sender.sendMessage("FireRate: " + gunStat.getFireRate());
                    sender.sendMessage("BulletSpeed: " + gunStat.getBulletSpeed());
                    sender.sendMessage("Penetration: " + gunStat.getPenetration());
                    sender.sendMessage("BulletPerShot: " + gunStat.getBulletPerShot());
                    sender.sendMessage("====================================");
                });
            }
        }
        return false;
    }
}
