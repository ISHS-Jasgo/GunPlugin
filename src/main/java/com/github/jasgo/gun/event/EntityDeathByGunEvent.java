package com.github.jasgo.gun.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;

public class EntityDeathByGunEvent extends EntityDeathEvent {

    private final Player killer;
    private final String gunName;

    public EntityDeathByGunEvent(LivingEntity entity, Player killer, String gunName) {
        super(entity, new ArrayList<>());
        this.killer = killer;
        this.gunName = gunName;
    }

    public Player getKiller() {
        return killer;
    }

    public String getGunName() {
        return gunName;
    }

}
