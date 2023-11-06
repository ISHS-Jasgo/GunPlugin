package com.github.jasgo.gun;

public class GunStat {
    private final String name;
    private final double damage;
    private final int magazine;
    private final double reload;
    private final double fireRate;
    private final double bulletSpeed;
    private final int bulletCount;
    private final Penetration penetration;

    private final int bulletPerShot;

    public GunStat(String name, double damage, int magazine, double reload, double fireRate, double bulletSpeed, int bulletCount, Penetration penetration, int bulletPerShot) {
        this.name = name;
        this.damage = damage;
        this.magazine = magazine;
        this.reload = reload;
        this.fireRate = fireRate;
        this.bulletSpeed = bulletSpeed;
        this.bulletCount = bulletCount;
        this.penetration = penetration;
        this.bulletPerShot = bulletPerShot;
    }


    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    public int getMagazine() {
        return magazine;
    }

    public double getReload() {
        return reload;
    }

    public double getFireRate() {
        return fireRate;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public Penetration getPenetration() {
        return penetration;
    }

    public int getBulletPerShot() {
        return bulletPerShot;
    }
}
