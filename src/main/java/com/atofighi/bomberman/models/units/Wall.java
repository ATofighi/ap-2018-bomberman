package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.game.WallPainter;

public class Wall extends Unit {

    public Wall(int x, int y) {
        super(x, y);
    }

    public GamePainter makePainter(PaintController.CellType[][] map) {
        return new WallPainter(this, map);
    }
}
