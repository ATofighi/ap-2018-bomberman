package com.atofighi.bomberman.controllers.common;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.units.*;
import com.atofighi.bomberman.models.units.Unit;
import com.atofighi.bomberman.util.Direction;

import java.util.Date;

public class GameHandle extends Thread {
    Game game;

    public GameHandle(Game game) {
        this.game = game;
    }

    private boolean winned = false;

    public void run() {
        while (!Thread.interrupted() && !winned) {
            synchronized (Game.globalLock) {
                if (game.getType() == Game.Type.ALONE_GAME || game.getType() == Game.Type.MULTI_PLAYER_CLIENT) {
                    game.getBoardPanel().setBoardBounds(game.showX(), game.showY());
                }

                if (game.getType() == Game.Type.ALONE_GAME || game.getType() == Game.Type.MULTI_PLAYER_SERVER) {
                    handleMoves();
                    handleMonsterDestroys();
                    handleBombDestroys();
                    handleGetPowerUps();
                }
                if (game.getType() == Game.Type.MULTI_PLAYER_CLIENT && game.getMyBombermanId() < 0) {
                    handleViewMove();
                }
                if (game.getType() == Game.Type.ALONE_GAME || game.getType() == Game.Type.MULTI_PLAYER_SERVER) {
                    handleLoss();
                }
                if (game.getType() == Game.Type.MULTI_PLAYER_CLIENT) {
                    handleGetMap();
                    handleGetChats();
                }
                if (game.getType() == Game.Type.ALONE_GAME || game.getType() == Game.Type.MULTI_PLAYER_SERVER) {
                    handleWin();
                }
            }
        }
    }

    private void handleViewMove() {
        Direction direction = game.getMapViewMoveDirection();
        if (direction != null) {
            game.setMyLocationX(game.getMyLocationX() + direction.dx() * 10);
            game.setMyLocationY(game.getMyLocationY() + direction.dy() * 10);
        }
    }

    private void handleMonsterDestroys() {
        synchronized (Game.unitsLock) {
            for (MonsterController monsterController : game.getMapController().getMonsters()) {
                if (monsterController.get().isAlive()) {
                    for (BombermanController bomberman : game.getMapController().getBombermans()) {
                        if (monsterController.getRectangle().hasIntersectionWith(bomberman.getRectangle())) {
                            bomberman.destroy(null);
                        }
                    }
                }
            }
        }
    }


    private void handleGetPowerUps() {
        synchronized (Game.unitsLock) {
            for (PowerUpController powerUpController : game.getMapController().getPowerUps()) {
                if (powerUpController.get().isAlive()) {
                    for (BombermanController bomberman : game.getMapController().getBombermans()) {
                        if (powerUpController.getRectangle().hasIntersectionWith(bomberman.getRectangle())) {
                            if (powerUpController.isVisible()) {
                                bomberman.addPowerUp(powerUpController);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleMoves() {
        synchronized (Game.unitsLock) {
            for (BombermanController bombermanController : game.getMapController().getBombermans()) {
                bombermanController.move();
            }
            for (MonsterController monsterController : game.getMapController().getMonsters()) {
                if (monsterController.get().isAlive()) {
                    monsterController.move();
                }
            }
        }
    }

    private void handleBombDestroys() {
        synchronized (Game.unitsLock) {
            BombController[] bombs = game.getMapController().getBombs().stream()
                    .filter((BombController bomb) -> (bomb.get().isAlive() && bomb.get().getDestroyAt() < Game.getCurrentTime())).toArray(BombController[]::new);
            for (BombController bomb : bombs) {
                bomb.destroy(game.getMapController().getBombermanById(bomb.get().getOwnerId()));
                game.getBoardPanel().shake();
            }
        }
    }

    private void handleLoss() {
        for (BombermanController bomberman : game.getMapController().getBombermans()) {
            int time = (GameConfiguration.gameTime - (Game.getCurrentTime() - game.getStartTime())) / 1000;
            int points = bomberman.get().getPoints() + Math.min(0, time);
            if (points < 0 && time < 0) {
                bomberman.lose();
                game.lose();
            }
        }
    }

    private void handleWin() {
        synchronized (Game.unitsLock) {
            if (game.getMap().getMonsters().stream().noneMatch(Unit::isAlive) &&
                    game.getMapController().get(game.getMap().getDoor().getX(), game.getMap().getDoor().getY())
                            .stream().noneMatch((u) -> u.get().isAlive() && u instanceof WallController)) {
                for (BombermanController bombermanController : game.getMapController().getBombermans()) {
                    if (bombermanController.getRectangle()
                            .hasIntersectionWith(game.getMapController().getDoor().getRectangle())) {
                        bombermanController.get().increasePoints(100);
                        game.win();
                        winned = true;
                        break;
                    }
                }
            }
        }
    }

    private void handleGetMap() {
        synchronized (Game.unitsLock) {
            game.setMap(game.getApp().getProtocol().getMap());
        }
    }

    long lastGetChats = 0;

    private void handleGetChats() {
        if (lastGetChats + 500 < new Date().getTime()) {
            lastGetChats = new Date().getTime();
            game.getChatPanel().getTextArea().setText(game.getApp().getProtocol().getChats());
            game.getChatPanel().getTextArea()
                    .setCaretPosition(game.getChatPanel().getTextArea().getDocument().getLength());
        }
    }
}
