package com.atofighi.bomberman.models.units;

public abstract class Monster extends MovableUnit {
    private final String type;
    private int level;

    public Monster(int level, int x, int y, int speed, String type) {
        super(x, y, speed);
        this.level = level;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
