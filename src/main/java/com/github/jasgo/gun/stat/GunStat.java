package com.github.jasgo.gun.stat;

public class GunStat {
    private final String name;
    private final double damage;
    private final int magazine;
    private final double reload;
    private final int fireRate;
    private final double bulletSpeed;
    private final Penetration penetration;

    private final int bulletPerShot;

    public GunStat(String name, double damage, int magazine, double reload, int fireRate, double bulletSpeed, Penetration penetration, int bulletPerShot) {
        this.name = name;
        this.damage = damage;
        this.magazine = magazine;
        this.reload = reload;
        this.fireRate = fireRate;
        this.bulletSpeed = bulletSpeed;
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

    public int getFireRate() {
        return fireRate;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public Penetration getPenetration() {
        return penetration;
    }

    public int getBulletPerShot() {
        return bulletPerShot;
    }
}
