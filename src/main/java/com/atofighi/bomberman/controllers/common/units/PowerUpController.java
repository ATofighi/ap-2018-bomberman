package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.models.units.PowerUp;
import com.atofighi.bomberman.models.units.Unit;
import com.atofighi.bomberman.models.units.Wall;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.views.util.Paintable;
import com.atofighi.bomberman.views.client.game.PowerUpPainter;

import static com.atofighi.bomberman.configs.BoardConfiguration.*;

public class PowerUpController extends InWallUnitController {

    private PowerUp powerUp;

    public PowerUpController(MapController mapController, PowerUp powerUp) {
        super(mapController);
        this.powerUp = powerUp;
    }

    @Override
    public Unit get() {
        return powerUp;
    }


    @Override
    protected int getNumberOfMonstersOnDestroy() {
        return GameConfiguration.monstersOfPowerUp;
    }

    @Override
    public Rectangle getRectangle() {
        synchronized (Game.unitsLock) {
            return new Rectangle(get().getX() * cellSize,
                    get().getY() * cellSize,
                    cellSize, cellSize);
        }
    }

    @Override
    public void destroy(Bomberman destroyer) {
        super.destroy(destroyer);
        if (isVisible()) {
            get().setAlive(false);
        }
    }
}
