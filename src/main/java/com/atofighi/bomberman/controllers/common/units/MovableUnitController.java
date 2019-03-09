package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Monster;
import com.atofighi.bomberman.models.units.MovableUnit;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.views.client.game.MovableUnitPainter;

import static com.atofighi.bomberman.configs.BoardConfiguration.*;

public abstract class MovableUnitController extends UnitController {


    public abstract MovableUnit get();


    protected MapController mapController;
    private int lastMove = 0;

    protected MovableUnitController(MapController mapController) {
        this.mapController = mapController;
    }

    protected boolean isTimeOfMove() {
        return Game.getCurrentTime() - lastMove >= 100.0 / get().getSpeed();
    }

    public void move() {
        synchronized (Game.unitsLock) {

            if (!get().isAlive()) {
                return;
            }
            if (!get().isInMove()) {
                return;

            }
            if (!isTimeOfMove())
                return;
            mapController.get(get().getX(), get().getY()).remove(this);
            lastMove = Game.getCurrentTime();
            Rectangle oldRectangle = getRectangle();
            switch (get().getDirection()) {
                case RIGHT:
                    get().setRealX(get().getRealX() + 1);
                    break;
                case LEFT:
                    get().setRealX(get().getRealX() - 1);
                    break;
                case UP:
                    get().setRealY(get().getRealY() - 1);
                    break;
                case DOWN:
                    get().setRealY(get().getRealY() + 1);
                    break;
            }

            boolean rollback = needRollback(get().getX(), get().getY(), getRectangle(), oldRectangle);

            if (rollback && (!get().isGhost()
                    || (get() instanceof Monster &&
                    needRollback(get().getX(), get().getY(), getRectangle(), oldRectangle, BombController.class))
                    || getRectangle().hasIntersectionWith(new Rectangle(0, 0,
                    cellSize, mapController.getMap().getHeight() * cellSize))
                    || getRectangle()
                    .hasIntersectionWith(new Rectangle(cellSize * (mapController.getMap().getWidth() - 1), 0,
                            cellSize, mapController.getMap().getHeight() * cellSize))
                    || getRectangle().hasIntersectionWith(new Rectangle(0, 0,
                    mapController.getMap().getWidth() * cellSize, cellSize))
                    || getRectangle()
                    .hasIntersectionWith(new Rectangle(0, cellSize * (mapController.getMap().getHeight() - 1),
                            mapController.getMap().getWidth() * cellSize, cellSize)))) {
                moveRollback();
                get().setInMove(false);
            }
            mapController.get(get().getX(), get().getY()).add(this);
        }
    }


    protected boolean needRollback(int x, int y, Rectangle rectangle, Rectangle oldRectangle) {
        return needRollback(x, y, rectangle, oldRectangle, WallController.class)
                || needRollback(x, y, rectangle, oldRectangle, StoneController.class)
                || needRollback(x, y, rectangle, oldRectangle, BombController.class);
    }

    protected boolean needRollback(int x, int y, Rectangle rectangle, Rectangle oldRectangle, Class type) {
        boolean rollback = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (UnitController unit : mapController.get(x + i, y + j)) {
                    if (unit.getClass() == type) {
                        if (rectangle.hasIntersectionWith(unit.getRectangle()) &&
                                !oldRectangle.hasIntersectionWith(unit.getRectangle()) &&
                                unit.get().isAlive()) {
                            rollback = true;
                        }
                    }
                }
            }
        }
        return rollback;
    }

    private void moveRollback() {
        synchronized (Game.unitsLock) {
            switch (get().getDirection()) {
                case RIGHT:
                    get().setRealX(get().getRealX() - 1);
                    break;
                case LEFT:
                    get().setRealX(get().getRealX() + 1);
                    break;
                case UP:
                    get().setRealY(get().getRealY() + 1);
                    break;
                case DOWN:
                    get().setRealY(get().getRealY() - 1);
                    break;
            }
        }
    }
}
