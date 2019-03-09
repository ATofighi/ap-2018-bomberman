package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.BombPainter;
import com.atofighi.bomberman.views.client.game.BombermanPainter;
import com.atofighi.bomberman.views.client.game.GamePainter;

public class Bomberman extends MovableUnit {

    private int bombLimit = 1;
    private int bombRange = GameConfiguration.defaultBombRange;
    private boolean controlBombs = false;
    private int points = 0;
    private int lastDeadTime = -1;
    private int lastShieldTime = 0;
    private int initialX, initialY;
    private String name = "AliReza";

    public Bomberman(int x, int y) {
        super(x, y, GameConfiguration.bombermanDefaultSpeed);
        initialX = getRealX();
        initialY = getRealY();
    }
    public Bomberman(String name, int x, int y) {
        this(x, y);
        this.name = name;
    }

    @Override
    public int getLegX() {
        return getRealX() + 32;
    }


    @Override
    public int getLegY() {
        return getRealY() + 57;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }

    public boolean canControlBombs() {
        return controlBombs;
    }

    public void setControlBombs(boolean controlBombs) {
        this.controlBombs = controlBombs;
    }

    public int getBombRange() {
        return bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public int getPoints() {
        return points;
    }

    public void increasePoints(int diff) {
        points += diff;
    }

    public int getLastDeadTime() {
        return lastDeadTime;
    }

    public void setLastDeadTime(int lastDeadTime) {
        this.lastDeadTime = lastDeadTime;
    }

    public int getLastShieldTime() {
        return lastShieldTime;
    }

    public void setLastShieldTime(int lastShieldTime) {
        this.lastShieldTime = lastShieldTime;
    }


    @Override
    public GamePainter makePainter(PaintController.CellType[][] map) {
        return new BombermanPainter(this);
    }

    public String getName() {
        return name;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    @Override
    public String toString() {
        return "Bomberman{" +
                "x=" + getRealX() +
                ", y=" + getRealY() +
                ", bombLimit=" + bombLimit +
                ", bombRange=" + bombRange +
                ", controlBombs=" + controlBombs +
                ", points=" + points +
                ", lastDeadTime=" + lastDeadTime +
                ", lastShieldTime=" + lastShieldTime +
                '}';
    }
}
