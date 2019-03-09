package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.game.PowerUpPainter;
import com.atofighi.bomberman.views.client.game.StonePainter;

public class Stone extends Unit {
    public Stone(int x, int y) {
        super(x, y);
    }


    @Override
    public GamePainter makePainter(PaintController.CellType[][] map) {
        return new StonePainter(this, map);
    }
}
