package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.models.units.Stone;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.views.client.game.StonePainter;

public class StoneController extends UnitController {
    private Stone stone;

    public StoneController(Stone stone, MapController mapController) {
        this.stone = stone;
    }

    public Stone get() {
        return stone;
    }

    @Override
    public void destroy(Bomberman destroyer) {
        // nothing :)
    }

    @Override
    public Rectangle getRectangle() {
        synchronized (Game.unitsLock) {
            return new Rectangle(get().getX() * BoardConfiguration.cellSize,
                    get().getY() * BoardConfiguration.cellSize, BoardConfiguration.cellSize, BoardConfiguration.cellSize);
        }
    }
}
