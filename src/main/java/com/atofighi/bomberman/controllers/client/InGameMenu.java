package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.views.client.game.InGameMenuPainter;

public class InGameMenu {
    private InGameMenuPainter inGameMenuPainter = new InGameMenuPainter(this);

    private boolean showMenu = false;
    private Game game;

    public InGameMenu(Game game) {
        this.game = game;
        inGameMenuPainter.getMenus().addMenu("Return To Game", () -> {
            showMenu = false;
        });


        inGameMenuPainter.getMenus().addMenu("Save Game", () -> {
            game.getApp().saveGame(game);
        });


        inGameMenuPainter.getMenus().addMenu("Load Game", () -> {
            game.getApp().loadGame(game);
        });

        inGameMenuPainter.getMenus().addMenu("Back To Home Page", () -> {
            game.close();
            game.getApp().showHome();
        });
        inGameMenuPainter.getMenus().addMenu("Exit Game", () -> {
            game.getApp().exit();
        });
    }

    public boolean isShowMenu() {

        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public InGameMenuPainter getInGameMenuPainter() {
        return inGameMenuPainter;
    }
}