package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.util.Direction;

public abstract class MovableUnit extends Unit {
    private int speed;
    private boolean ghost = false;
    private Direction direction = Direction.DOWN;
    private boolean inMove = false;
    private int realX, realY;

    public MovableUnit(int x, int y, int speed) {
        super(x, y);
        this.realX = x * BoardConfiguration.cellSize;
        this.realY = y * BoardConfiguration.cellSize;
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isInMove() {
        return inMove;
    }

    public void setInMove(boolean inMove) {
        this.inMove = inMove;
    }


    @Override
    public int getX() {
        return getLegX() / BoardConfiguration.cellSize;
    }

    @Override
    public void setX(int x) {
    }

    @Override
    public int getY() {
        return getLegY() / BoardConfiguration.cellSize;
    }

    public abstract int getLegX();

    public abstract int getLegY();

    @Override
    public void setY(int y) {
    }

    public int getRealX() {
        return realX;
    }

    public int getRealY() {
        return realY;
    }

    public void setRealX(int x) {
        this.realX = x;
    }

    public void setRealY(int y) {
        this.realY = y;
    }



    public boolean isGhost() {
        return ghost;
    }

    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }

    @Override
    public String toString() {
        return "MovableUnit{" +
                "speed=" + speed +
                ", direction=" + direction +
                ", inMove=" + inMove +
                '}';
    }

}
