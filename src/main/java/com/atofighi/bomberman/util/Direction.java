package com.atofighi.bomberman.util;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public static Direction random() {
        return Direction.values()[(int) (Math.random() * 4)];
    }

    public int dx() {
        switch (this) {
            case RIGHT:
                return 1;
            case LEFT:
                return -1;
            default:
                return 0;
        }
    }
    public int dy() {
        switch (this) {
            case DOWN:
                return 1;
            case UP:
                return -1;
            default:
                return 0;
        }
    }
}
