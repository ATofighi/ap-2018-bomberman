package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.*;
import com.atofighi.bomberman.util.Direction;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.util.Timer;
import com.atofighi.bomberman.views.client.game.BombermanPainter;

import java.util.Optional;

public class BombermanController extends MovableUnitController {
    private Bomberman bomberman;
    private Timer refreshTimer = null;

    public BombermanController(Bomberman bomberman, MapController mapController) {
        super(mapController);
        this.bomberman = bomberman;

        if (!bomberman.isAlive()) {
            refresh();
        }
    }

    public Bomberman get() {
        return bomberman;
    }

    public void addBomb() {
        synchronized (Game.unitsLock) {
            if (!bomberman.isAlive()) {
                return;
            }
            if (mapController.getBombs().stream().filter((controller) ->
                    controller.get().getOwnerId() == bomberman.getId() &&
                            controller.get().isAlive()).count() >= get().getBombLimit()) {
                return;
            }
            if (mapController.get(bomberman.getX(), bomberman.getY()).stream().noneMatch((u) -> {
                Unit unit = u.get();
                return unit.isAlive() && (unit instanceof Bomb
                        || unit instanceof Wall || unit instanceof Stone);
            })) {
                mapController.addBomb(new Bomb(bomberman, bomberman.getX(), bomberman.getY(),
                        (bomberman.canControlBombs() ? -1 : GameConfiguration.bombAliveTime)));
            }
        }
    }

    public void destroy(Bomberman destroyer) {
        synchronized (Game.unitsLock) {
            if (!bomberman.isAlive() ||
                    Game.getCurrentTime() - bomberman.getLastShieldTime() < GameConfiguration.shieldTime) {
                return;
            }

            if (destroyer != null) {
                destroyer.increasePoints(+100);
            }
            bomberman.increasePoints(-200);

            bomberman.setAlive(false);
            bomberman.setLastDeadTime(Game.getCurrentTime());
            resetToDefaults();

            if (refreshTimer != null) {
                refreshTimer.interrupt();
            }

            refreshTimer = new Timer(GameConfiguration.refreshTime, this::refresh);
            refreshTimer.start();
        }

    }

    public void refresh() {
        synchronized (Game.unitsLock) {
            mapController.get(bomberman.getX(), bomberman.getY()).remove(this);
            bomberman.setAlive(true);
            bomberman.setInMove(false);
            bomberman.setDirection(Direction.RIGHT);

            bomberman.setLastShieldTime(Game.getCurrentTime());

            bomberman.setRealX(bomberman.getInitialX());
            bomberman.setRealY(bomberman.getInitialY());
            mapController.get(bomberman.getX(), bomberman.getY()).add(this);
        }
    }

    private void resetToDefaults() {
        synchronized (Game.unitsLock) {
            bomberman.setControlBombs(false);
            bomberman.setBombLimit(1);
            bomberman.setGhost(false);
            bomberman.setSpeed(GameConfiguration.bombermanDefaultSpeed);
            bomberman.setBombRange(GameConfiguration.defaultBombRange);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(bomberman.getRealX() + 17, bomberman.getRealY() + 23, 30, 40);
    }

    public void addPowerUp(PowerUpController powerUpController) {
        synchronized (Game.unitsLock) {
            PowerUp powerUp = (PowerUp) powerUpController.get();
            boolean done = false;
            switch (powerUp.getName()) {
                case BOMB_NUMBER:
                    if (powerUp.getType() == PowerUp.Type.INCREMENT) {
                        get().setBombLimit(get().getBombLimit() + 1);
                        done = true;
                    } else {
                        if (get().getBombLimit() > 1) {
                            done = true;
                            get().setBombLimit(get().getBombLimit() - 1);
                        }
                    }
                    break;
                case BOMB_RADIUS:
                    if (powerUp.getType() == PowerUp.Type.INCREMENT) {
                        get().setBombRange(get().getBombRange() + 1);
                        done = true;
                    } else {
                        if (get().getBombRange() > 1) {
                            done = true;
                            get().setBombRange(get().getBombRange() - 1);
                        }
                    }
                    break;
                case BOMBERMAN_SPEED:
                    if (powerUp.getType() == PowerUp.Type.INCREMENT) {
                        done = true;
                        get().setSpeed(get().getSpeed() + 4);
                    } else {
                        if (get().getSpeed() > 12) {
                            get().setSpeed(get().getSpeed() - 4);
                            done = true;
                        }
                    }
                    break;
                case POINTS:
                    if (powerUp.getType() == PowerUp.Type.INCREMENT) {
                        bomberman.increasePoints(100);
                    } else {
                        bomberman.increasePoints(-100);
                    }
                    done = true;
                    break;
                case CONTROL_BOMB:
                    if (powerUp.getType() == PowerUp.Type.INCREMENT) {
                        if (!get().canControlBombs()) {
                            get().setControlBombs(true);
                            done = true;
                        }
                    } else {
                        if (get().canControlBombs()) {
                            get().setControlBombs(false);
                            done = true;
                        }
                    }
                    break;
                case GHOST:
                    if (powerUp.getType() == PowerUp.Type.INCREMENT) {
                        if (!get().isGhost()) {
                            get().setGhost(true);
                            done = true;
                        }
                    } else {
                        if (get().isGhost()) {
                            get().setGhost(false);
                            done = true;
                        }
                    }
                    break;
            }
            if (done) {
                powerUp.setAlive(false);
            }
        }
    }

    public void destroyBomb() {
        synchronized (Game.unitsLock) {
            Optional<BombController> bomb = mapController.getBombs().stream().filter((bombController ->
                    bombController.get().isAlive() && bombController.get().getOwnerId() == bomberman.getId()
                            && bombController.get().getDestroyAt() > Game.getCurrentTime() + 10000
            )).findFirst();
            bomb.ifPresent(bombController -> bombController.destroy(bomberman));
        }
    }

    public void lose() {
        synchronized (Game.unitsLock) {
            bomberman.setAlive(false);
        }
    }

    public Timer getRefreshTimer() {
        return refreshTimer;
    }

    public void setRefreshTimer(Timer refreshTimer) {
        this.refreshTimer = refreshTimer;
    }
}
