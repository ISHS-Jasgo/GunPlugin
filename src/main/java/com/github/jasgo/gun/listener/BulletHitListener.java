package com.github.jasgo.gun.listener;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.github.jasgo.gun.Gun;
import com.github.jasgo.gun.bullet.BulletEntity;
import com.github.jasgo.gun.bullet.BulletEntityManager;
import com.github.jasgo.gun.bullet.DamageCalculator;
import com.github.jasgo.gun.event.EntityDeathByGunEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BulletHitListener implements Listener {

    public List<UUID> deadEntityList = new ArrayList<>();

    @EventHandler
    public void onBulletHit(ProjectileCollideEvent event) {
        if (event.getCollidedWith().getType().isAlive() && event.getEntity().getType().equals(EntityType.SNOWBALL)) {
            if (!BulletEntityManager.isBulletEntity(event.getEntity().getUniqueId())) return;
            if (event.getCollidedWith().equals(event.getEntity().getShooter())) event.setCancelled(true);
            BulletEntity bulletEntity = BulletEntityManager.getBulletEntityByUUID(event.getEntity().getUniqueId());
            double damage = DamageCalculator.calculateDamage(bulletEntity.getBullet());
            LivingEntity target = (LivingEntity) event.getCollidedWith();
            if (target.getHealth() <= damage) {
                Player killer = (Player) event.getEntity().getShooter();
                EntityDeathEvent entityDeathEvent = new EntityDeathByGunEvent(target, killer, bulletEntity.getBullet().getName());
                target.setHealth(0);
                Bukkit.getPluginManager().callEvent(entityDeathEvent);
                if (entityDeathEvent.isCancelled()) return;
            }
            target.setNoDamageTicks(0);
            target.damage(damage);
            BulletEntityManager.despawn(bulletEntity);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathByGunEvent event) {
        if (deadEntityList.contains(event.getEntity().getUniqueId())) return;
        event.getKiller().sendMessage("§7You killed §c" + event.getEntity().getName() + " §7with §c" + event.getGunName());
        Bukkit.broadcastMessage("§7" + event.getKiller().getName() + " §7killed §c" + event.getEntity().getName() + " §7with §c" + event.getGunName());
        UUID uuid = event.getEntity().getUniqueId();
        deadEntityList.add(uuid);
        new BukkitRunnable() {
            @Override
            public void run() {
                deadEntityList.remove(uuid);
            }
        }.runTaskLater(Gun.getInstance(), 20 * 10);
    }
}
