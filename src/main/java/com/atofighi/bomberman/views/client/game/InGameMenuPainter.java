package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.controllers.client.InGameMenu;
import com.atofighi.bomberman.views.client.menu.Menus;

import java.awt.*;

import static com.atofighi.bomberman.configs.BoardConfiguration.boardWidth;
import static com.atofighi.bomberman.configs.BoardConfiguration.cellSize;
import static com.atofighi.bomberman.configs.GameMenuConfiguration.menuWidth;

public class InGameMenuPainter extends GamePainter {

    private Menus menus = new Menus((cellSize*boardWidth - menuWidth) / 2, 200);
    private InGameMenu inGameMenu;

    public InGameMenuPainter(InGameMenu inGameMenu) {
        super();
        this.inGameMenu = inGameMenu;
    }

    @Override
    public void paint(Graphics2D g2) {
        if (inGameMenu.isShowMenu()) {
            g2.setColor(new Color(0, 0, 0, 0.8f));
            int width = boardWidth * cellSize;
            int height = BoardConfiguration.boardHeight * cellSize;
            g2.fillRect(0, 0, width, height);
            menus.paint(g2);
        }
    }

    public Menus getMenus() {
        return menus;
    }

    @Override
    public int zIndex() {
        return 10000;
    }
}
