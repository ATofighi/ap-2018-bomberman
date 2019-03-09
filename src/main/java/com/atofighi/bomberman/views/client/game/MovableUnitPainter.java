package com.atofighi.bomberman.views.client.game;

public abstract class MovableUnitPainter extends GamePainter {
    public MovableUnitPainter() {
        super();
    }


    @Override
    public int zIndex() {
        return 5;
    }
}
