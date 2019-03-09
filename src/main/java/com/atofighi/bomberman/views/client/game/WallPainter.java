package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.controllers.common.units.MapController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.Wall;

public class WallPainter extends StoneWallPainter {

    public WallPainter(Wall wall, PaintController.CellType[][] map) {
        super(wall, "wall/%s.png", map);
    }

    public int zIndex() {
        return 2;
    }


}
