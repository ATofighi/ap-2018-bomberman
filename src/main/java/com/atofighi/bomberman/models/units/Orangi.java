package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.game.SimpleMonsterPainter;

// Level 3 Monster
public class Orangi extends Monster {


    public Orangi(int x, int y) {
        super(3, x, y, GameConfiguration.bombermanDefaultSpeed, "Orangi");
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
        return new SimpleMonsterPainter("orangi", this);
    }
}
