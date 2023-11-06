package com.github.jasgo.gun.bullet;

import org.bukkit.entity.Snowball;

import java.util.UUID;

public class BulletEntity {
    private final Bullet bullet;

    private Snowball bulletEntity;

    public BulletEntity(Bullet bullet) {
        this.bullet = bullet;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public Snowball getBulletEntity() {
        return bulletEntity;
    }

    public void setBulletEntity(Snowball bulletEntity) {
        this.bulletEntity = bulletEntity;
    }
}
