package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.BombermanPainter;
import com.atofighi.bomberman.views.client.game.DoorPainter;
import com.atofighi.bomberman.views.client.game.GamePainter;

public class Door extends Unit {
    public Door(int x, int y) {
        super(x, y);
    }


    @Override
    public GamePainter makePainter(PaintController.CellType[][] map) {
        return new DoorPainter(this);
    }
}
