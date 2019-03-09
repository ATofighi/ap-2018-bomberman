package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.util.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    private Game game;

    private String cheatString = "";

    public GameKeyListener(Game game) {
        this.game = game;
    }

    private Direction getDirection(int charCode) {
        switch (charCode) {
            case 37:
                return Direction.LEFT;
            case 38:
                return Direction.UP;
            case 39:
                return Direction.RIGHT;
            case 40:
                return Direction.DOWN;
        }
        return null;
    }

    public void keyTyped(KeyEvent e) {
        if (((int) e.getKeyChar() >= (int) 'a' && (int) e.getKeyChar() <= (int) 'z')
                || ((int) e.getKeyChar() >= (int) '0' && (int) e.getKeyChar() <= (int) '9')
                || e.getKeyChar() == ' ') {
            cheatString += e.getKeyChar();
        }
        if (game.getInGameMenu().isShowMenu()) {
            game.getInGameMenu().getInGameMenuPainter().getMenus().getKeyListener().keyTyped(e);
        }
    }

    public void keyPressed(KeyEvent e) {
        synchronized (Game.unitsLock) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_TAB:
                    game.getChatPanel().getTextField().requestFocus();
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_DOWN:
                    if (!game.isPaused()) {
                        Direction direction = getDirection(e.getKeyCode());
                        if (direction != null) {
                            if (game.myBomberman() != null) {
                                game.performAction(game.myBomberman(), Game.Action.press(direction));
                            } else {
                                game.setMapViewMoveDirection(direction);
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    game.cheat(cheatString);
                    cheatString = "";
                    break;
                case KeyEvent.VK_ESCAPE:
                    game.getInGameMenu().setShowMenu(!game.getInGameMenu().isShowMenu());
                    break;
                case KeyEvent.VK_O:
                    if (!game.isPaused()) {
                        if (e.isControlDown()) {
                            game.setPaused(true);
                            game.getApp().loadGame(game);
                            game.setPaused(false);
                        }
                    }
                    break;
                case KeyEvent.VK_S:
                    if (!game.isPaused()) {
                        if (e.isControlDown()) {
                            if (game.getType() == Game.Type.ALONE_GAME) {
                                game.getApp().saveGame(game);
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_B:
                    if (game.myBomberman() != null) {
                        game.performAction(game.myBomberman(), Game.Action.ADD_BOMB);
                    }
                    break;
                case KeyEvent.VK_C:
                    if (game.myBomberman() != null) {
                        game.performAction(game.myBomberman(), Game.Action.CONTROL);
                    }
                    break;
            }
        }
        if (game.getInGameMenu().isShowMenu()) {
            game.getInGameMenu().getInGameMenuPainter().getMenus().getKeyListener().keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (game.myBomberman() != null) {
            if (getDirection(e.getKeyCode()) == game.myBomberman().getDirection()) {
                game.performAction(game.myBomberman(), Game.Action.release(game.myBomberman().getDirection()));
            }
        } else if (game.getType() == Game.Type.MULTI_PLAYER_CLIENT && game.getMyBombermanId() < 0) {
            if (getDirection(e.getKeyCode()) == game.getMapViewMoveDirection()) {
                game.setMapViewMoveDirection(null);
            }
        }
        if (game.getInGameMenu().isShowMenu()) {
            game.getInGameMenu().getInGameMenuPainter().getMenus().getKeyListener().keyReleased(e);
        }
    }
}
