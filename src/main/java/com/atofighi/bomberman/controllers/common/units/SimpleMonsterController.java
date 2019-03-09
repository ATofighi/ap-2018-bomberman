package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.util.Direction;
import com.atofighi.bomberman.util.Rectangle;

public abstract class SimpleMonsterController extends MonsterController {
    private int lastRandomMove = 0;
    public SimpleMonsterController(MapController mapController) {
        super(mapController);
    }

    public Rectangle getRectangle() {
        synchronized (Game.unitsLock) {
            return new Rectangle(get().getRealX() + 8, get().getRealY() + 19,
                    49, 44);
        }
    }

    protected void randomMove(int time) {
        if (Game.getCurrentTime() - lastRandomMove > time) {
            lastRandomMove = Game.getCurrentTime();
            get().setDirection(Direction.random());
        }
        int x = get().getRealX(), y = get().getRealY();
        super.move();
        if (x == get().getRealX() && y == get().getRealY()) {
            get().setDirection(Direction.random());
            super.move();
        }
    }
}
