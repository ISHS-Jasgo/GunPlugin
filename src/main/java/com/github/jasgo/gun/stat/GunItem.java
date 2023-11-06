package com.github.jasgo.gun.stat;

import org.bukkit.inventory.ItemStack;

public class GunItem {

    private final ItemStack item;
    private final GunStat stat;

    public GunItem(ItemStack item, GunStat stat) {
        this.item = item;
        this.stat = stat;
    }

    public ItemStack getItem() {
        return item;
    }

    public GunStat getStat() {
        return stat;
    }

}
