package com.github.jasgo.gun.bullet;

import com.github.jasgo.gun.stat.Penetration;

public class Bullet {

    private final String name;
    private final double damage;
    private final double speed;

    private final Penetration penetration;

    public Bullet(String name, double damage, double speed, Penetration penetration) {
        this.name = name;
        this.damage = damage;
        this.speed = speed;
        this.penetration = penetration;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public Penetration getPenetration() {
        return penetration;
    }

}
