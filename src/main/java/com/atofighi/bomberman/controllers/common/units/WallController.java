package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.models.units.Wall;
import com.atofighi.bomberman.util.Direction;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.views.client.game.WallPainter;

public class WallController extends UnitController {
    private Wall wall;
    MapController mapController;

    public WallController(Wall wall, MapController mapController) {
        this.mapController = mapController;
        this.wall = wall;
    }

    public Wall get() {
        return wall;
    }


    @Override
    public void destroy(Bomberman destroyer) {
        if (wall.isAlive()) {
            if (destroyer != null) {
                destroyer.increasePoints(10);
            }
            wall.setAlive(false);
        }
    }


    @Override
    public Rectangle getRectangle() {
        return new Rectangle(get().getX() * BoardConfiguration.cellSize,
                get().getY() * BoardConfiguration.cellSize, BoardConfiguration.cellSize, BoardConfiguration.cellSize);
    }
}
