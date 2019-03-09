package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.game.SimpleMonsterPainter;

// Level 2 Monster
public class Bluei extends Monster {


    public Bluei(int x, int y) {
        super(2, x, y, GameConfiguration.bombermanDefaultSpeed / 2, "Bleui");
    }

    @Override
    public int getLegX() {
        return getRealX() + 32;
    }

    @Override
    public int getLegY() {
        return getRealY() + 56;
    }


    @Override
    public GamePainter makePainter(PaintController.CellType[][] map) {
        return new SimpleMonsterPainter("bluei", this);
    }

}
