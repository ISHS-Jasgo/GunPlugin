package com.github.jasgo.gun.listener;

import com.github.jasgo.gun.Gun;
import com.github.jasgo.gun.bullet.Bullet;
import com.github.jasgo.gun.bullet.BulletEntity;
import com.github.jasgo.gun.bullet.BulletEntityManager;
import com.github.jasgo.gun.manager.GunDataManager;
import com.github.jasgo.gun.stat.GunItem;
import com.github.jasgo.gun.stat.GunStat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class GunShootListener implements Listener {

    @EventHandler
    public void onShoot(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            List<GunItem> gunItems = GunDataManager.gunLoader.getGunItems();
            for (GunItem gunItem : gunItems) {
                if (gunItem.getItem().getType().equals(event.getItem().getType())) {
                    if (event.getPlayer().getCooldown(gunItem.getItem().getType()) > 0) {
                        return;
                    }
                    if (event.getItem().getAmount() > 1) {
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else {
                        reload(event.getPlayer(), gunItem, findGunSlotIndex(event.getPlayer(), gunItem));
                        return;
                    }
                    Player player = event.getPlayer();
                    spawnBullet(player, gunItem);
                    player.setCooldown(gunItem.getItem().getType(), gunItem.getStat().getFireRate());
                    event.setCancelled(true);
                }
            }
        }

    }

    public void reload(Player player, GunItem gunItem, int slot) {
        final double[] time = {gunItem.getStat().getReload()};
        player.setCooldown(gunItem.getItem().getType(), (int) Math.floor(gunItem.getStat().getReload()));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (time[0] <= 0) {
                    player.getInventory().getItem(slot).setAmount(gunItem.getStat().getMagazine());
                    cancel();
                    return;
                }
                time[0] -= 1;
                double sec = Math.round(time[0] * 100) / 100.0 / 20.0;
                String actionBar = String.format("§aReloading... %.2fs", sec);
                player.sendActionBar(actionBar);
            }
        }.runTaskTimer(Gun.getInstance(), 0, 1);
    }

    public int findGunSlotIndex(Player player, GunItem gunItem) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i).equals(gunItem.getItem())) {
                return i;
            }
        }
        return -1;
    }

    public void spawnBullet(Player player, GunItem gunItem) {
        GunStat stat = gunItem.getStat();
        Bullet bullet = new Bullet(stat.getName(), stat.getDamage(), stat.getBulletSpeed(), stat.getPenetration());
        for (int i = 0; i < stat.getBulletPerShot(); i++) {
            double rrx = Math.random() * stat.getBulletPerShot() * (Math.random() > 0.5 ? 1 : -1);
            double rry = Math.random() * stat.getBulletPerShot() * (Math.random() > 0.5 ? 1 : -1);
            double rrz = Math.random() * stat.getBulletPerShot() * (Math.random() > 0.5 ? 1 : -1);
            Vector direction = player.getEyeLocation().getDirection();
            direction = rotate(direction, rrx, rry, rrz);
            BulletEntity bulletEntity = new BulletEntity(bullet);
            BulletEntityManager.spawn(player, player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(2)), bulletEntity);
            BulletEntityManager.launch(direction, bulletEntity);
        }
    }

    public Vector rotateX(Vector vector, double angle) {
        double rad = Math.toRadians(angle);
        double y = vector.getY() * Math.cos(rad) - vector.getZ() * Math.sin(rad);
        double z = vector.getY() * Math.sin(rad) + vector.getZ() * Math.cos(rad);
        return vector.setY(y).setZ(z);
    }

    public Vector rotateY(Vector vector, double angle) {
        double rad = Math.toRadians(angle);
        double x = vector.getX() * Math.cos(rad) - vector.getZ() * Math.sin(rad);
        double z = vector.getX() * Math.sin(rad) + vector.getZ() * Math.cos(rad);
        return vector.setX(x).setZ(z);
    }

    public Vector rotateZ(Vector vector, double angle) {
        double rad = Math.toRadians(angle);
        double x = vector.getX() * Math.cos(rad) - vector.getY() * Math.sin(rad);
        double y = vector.getX() * Math.sin(rad) + vector.getY() * Math.cos(rad);
        return vector.setX(x).setY(y);
    }

    public Vector rotate(Vector vector, double angleX, double angleY, double angleZ) {
        return rotateX(rotateY(rotateZ(vector, angleZ), angleY), angleX);
    }
}
