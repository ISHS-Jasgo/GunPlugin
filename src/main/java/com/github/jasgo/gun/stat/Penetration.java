package com.github.jasgo.gun.stat;

public enum Penetration {
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4);

    private final int level;

    Penetration(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
