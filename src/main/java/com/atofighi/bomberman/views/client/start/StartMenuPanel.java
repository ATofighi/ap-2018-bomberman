package com.atofighi.bomberman.views.client.start;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.configs.WindowConfiguration;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.menu.Menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class StartMenuPanel extends JPanel {
    private static transient BufferedImage image = null;
    private Menus[] menus = {new Menus(150, 150), new Menus(150, 150)};
    private int currentMenu = 0;

    public StartMenuPanel() {
        image = GamePainter.getImage("mainMenu.png");

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                menus[currentMenu].getKeyListener().keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                menus[currentMenu].getKeyListener().keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                menus[currentMenu].getKeyListener().keyReleased(e);
            }
        });
    }

    public Menus getMenus() {
        return menus[0];
    }

    public Menus getMultiplayerMenu() {
        return menus[1];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(image, 0, 0, null);

        menus[currentMenu].paint(g2);

        g2.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 14));
        g2.setColor(Color.white);
        g2.drawString("version: " + GameConfiguration.version, 10, WindowConfiguration.height - 10);
    }

    public void setCurrentMenu(int currentMenu) {
        this.currentMenu = currentMenu;
    }
}
