package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.models.units.Monster;

public abstract class MonsterController extends MovableUnitController {

    public MonsterController(MapController mapController) {
        super(mapController);
    }

    @Override
    public abstract Monster get();

    @Override
    public void move() {
        if (mapController.get(get().getX(), get().getY())
                .stream().anyMatch((unit) -> unit instanceof BombController && ((BombController) unit).get().isAlive()))
            return;
        super.move();
    }

    @Override
    public void destroy(Bomberman destroyer) {
        synchronized (Game.unitsLock) {
            if (get().isAlive()) {
                if (destroyer != null) {
                    destroyer.increasePoints(20 * get().getLevel());
                }
                get().setAlive(false);
            }
        }
    }
}
