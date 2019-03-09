package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.models.units.Door;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.views.client.game.DoorPainter;

public class DoorController extends InWallUnitController {
    private Door door;

    public DoorController(MapController mapController, Door door) {
        super(mapController);
        this.door = door;
    }

    @Override
    public Door get() {
        return door;
    }

    @Override
    public Rectangle getRectangle() {
        synchronized (Game.unitsLock) {
            return new Rectangle(get().getX() * BoardConfiguration.cellSize,
                    get().getY() * BoardConfiguration.cellSize, BoardConfiguration.cellSize, BoardConfiguration.cellSize);
        }
    }

    @Override
    protected int getNumberOfMonstersOnDestroy() {
        return GameConfiguration.monstersOfDoor;
    }
}
