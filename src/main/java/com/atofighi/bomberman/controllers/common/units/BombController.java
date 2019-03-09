package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.*;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.util.Timer;
import com.atofighi.bomberman.views.client.game.BombPainter;

public class BombController extends UnitController {
    private Bomb bomb;
    private MapController mapController;

    public BombController(Bomb bomb, MapController mapController) {
        this.bomb = bomb;
        this.mapController = mapController;
    }


    public Bomb get() {
        return bomb;
    }

    public void destroy(Bomberman destroyer) {
        synchronized (Game.unitsLock) {
            if (bomb.isAlive()) {
                bomb.setDestroyAt(Game.getCurrentTime());
                bomb.setAlive(false);
                int[] range = new int[4];
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i <= bomb.getRange(); i++) {
                        int x = bomb.getX();
                        int y = bomb.getY();
                        if (j == 0) {
                            y -= i;
                        } else if (j == 1) {
                            x += i;
                        } else if (j == 2) {
                            y += i;
                        } else {
                            x -= i;
                        }
                        range[j] = i;

                        boolean stone = false;
                        if (mapController.get(x, y).stream().anyMatch((unit) ->
                                (unit instanceof WallController || unit instanceof StoneController)
                                        && unit.get().isAlive())) {
                            stone = true;
                        }

                        {
                            int finalX = x, finalY = y;
                            new Timer(i * GameConfiguration.bombDestroyDelay, () -> {
                                mapController.destroy(destroyer, finalX, finalY);
                            }).start();
                        }
                        if (stone)
                            break;

                    }
                    get().setDestroyRange(range);
                }
            }
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(get().getX() * BoardConfiguration.cellSize,
                get().getY() * BoardConfiguration.cellSize, BoardConfiguration.cellSize, BoardConfiguration.cellSize);
    }
}
