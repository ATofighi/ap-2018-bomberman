package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.GamePainter;

public abstract class Unit {
    private int x;
    private int y;
    private boolean alive = true;
    private final int id;
    private static int autoIncrementId = 1;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Unit(int x, int y) {
        id = autoIncrementId++;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getId() {
        return id;
    }

    public static int getAutoIncrementId() {
        return autoIncrementId;
    }

    public static void setAutoIncrementId(int autoIncrementId) {
        Unit.autoIncrementId = autoIncrementId;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", alive=" + alive +
                '}';
    }


    public abstract GamePainter makePainter(PaintController.CellType[][] map);
}
