package com.github.jasgo.gun.bullet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BulletEntityManager {

    public static List<BulletEntity> bulletEntityList = new ArrayList<>();

    public static void spawn(Player shooter, Location location, BulletEntity bulletEntity) {
        Snowball bullet = Bukkit.getWorld("world").spawn(location, Snowball.class);
        bullet.setShooter(shooter);
        bullet.setGravity(false);
        bulletEntity.setBulletEntity(bullet);
        bulletEntityList.add(bulletEntity);
    }

    public static void launch(Vector direction, BulletEntity bulletEntity) {
        bulletEntity.getBulletEntity().setVelocity(direction.multiply(bulletEntity.getBullet().getSpeed()));
    }

    public static void despawn(BulletEntity bulletEntity) {
        bulletEntityList.remove(bulletEntity);
        bulletEntity.getBulletEntity().remove();
    }

    public static BulletEntity getBulletEntityByUUID(UUID uuid) {
        for (BulletEntity bulletEntity : bulletEntityList) {
            if (bulletEntity.getBulletEntity().getUniqueId().equals(uuid)) {
                return bulletEntity;
            }
        }
        return null;
    }

    public static boolean isBulletEntity(UUID uuid) {
        for (BulletEntity bulletEntity : bulletEntityList) {
            if (bulletEntity.getBulletEntity().getUniqueId().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

}
