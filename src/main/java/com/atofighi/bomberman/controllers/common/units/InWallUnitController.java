package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.models.units.Wall;

public abstract class InWallUnitController extends UnitController{
    protected MapController mapController;

    public InWallUnitController(MapController mapController) {
        this.mapController = mapController;
    }

    public boolean isVisible() {
        return mapController.get(get().getX(), get().getY())
                .stream().noneMatch((unit) -> unit.get().isAlive() && unit.get() instanceof Wall);
    }

    @Override
    public void destroy(Bomberman destroyer) {
        synchronized (Game.unitsLock) {
            if (isVisible() && get().isAlive()) {
                for (int i = 0; i < getNumberOfMonstersOnDestroy(); i++) {
                    mapController.addRandomMonster(get().getX(), get().getY());
                }
            }
        }
    }

    protected abstract int getNumberOfMonstersOnDestroy();
}
