package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.controllers.common.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.atofighi.bomberman.configs.BoardConfiguration.*;


public class BoardPanel extends JPanel {

    private Game game;

    private int lastShake = -2000;

    private int translateX, translateY;

    private Font font;

    public BoardPanel() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getClassLoader().getResourceAsStream("good-times.ttf"));
            font = font.deriveFont(Font.PLAIN, 9);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        if (!game.getInGameMenu().isShowMenu() && !game.getLosePainter().isLosed()) {
            g2.translate(translateX, translateY);
        }
        g2.setColor(Color.decode("#42383a"));
        g2.fillRect(-100, -100, Math.max(boardWidth, game.getMap().getWidth()) * cellSize + 200,
                Math.max(boardHeight, game.getMap().getHeight()) * cellSize + 200);
        g2.setFont(font);
        game.getPainter().paint(g2);

        game.getLosePainter().paint(g2);

        if (game.getInGameMenu().isShowMenu()) {
            game.getInGameMenu().getInGameMenuPainter().paint(g2);
        }

    }

    public void setGame(Game game) {
        this.game = game;
        /*setBounds(0, 0, map.getWidth() * BoardConfiguration.cellSize,
                map.getHeight() * BoardConfiguration.cellSize);*/
    }


    public void setBoardBounds(int legX, int legY) {
        int maxWidth = boardWidth * cellSize;
        int maxHeight = boardHeight * cellSize;
        int currentWidth = Math.max(boardWidth, game.getMap().getWidth()) * cellSize;
        int currentHeight = Math.max(boardHeight, game.getMap().getHeight()) * cellSize;
        int x = (int) (legX - 0.5 * maxWidth);
        int y = (int) (legY - 0.5 * maxHeight);

        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (maxWidth + x > currentWidth) {
            x = currentWidth - maxWidth;
        }

        if (maxHeight + y > currentHeight) {
            y = currentHeight - maxHeight;
        }
        if (Game.getCurrentTime() - lastShake < 50) {
            x += 3;
        } else if (Game.getCurrentTime() - lastShake < 100) {
            y += 3;
        } else if (Game.getCurrentTime() - lastShake < 150) {
            x -= 3;
        } else if (Game.getCurrentTime() - lastShake < 200) {
            y -= 3;
        }

        this.translateX = -x;
        this.translateY = -y;
        /*setBounds(0, 0, map.getWidth() * BoardConfiguration.cellSize,
                map.getHeight() * BoardConfiguration.cellSize);*/
    }

    public void shake() {
        lastShake = Game.getCurrentTime();
    }
}
