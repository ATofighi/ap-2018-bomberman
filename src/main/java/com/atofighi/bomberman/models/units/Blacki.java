package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.game.SimpleMonsterPainter;

// Level 1 Monster
public class Blacki extends Monster {


    public Blacki(int x, int y) {
        super(1, x, y, GameConfiguration.bombermanDefaultSpeed / 2, "Blacki");
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
        return new SimpleMonsterPainter("blacki", this);
    }
}
