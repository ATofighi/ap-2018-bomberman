package com.atofighi.bomberman.views.client.game;


import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.controllers.common.units.MapController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.Stone;

public class StonePainter extends StoneWallPainter {
    public StonePainter(Stone stone, PaintController.CellType[][] map) {
        super(stone, "stone/%s.png", map);
    }

}
