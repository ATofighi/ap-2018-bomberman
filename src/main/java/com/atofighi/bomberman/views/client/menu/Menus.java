package com.atofighi.bomberman.views.client.menu;

import com.atofighi.bomberman.views.util.Paintable;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static com.atofighi.bomberman.configs.GameMenuConfiguration.*;

public class Menus implements Paintable {
    List<Menu> menus = new ArrayList<>(0);
    int selectedIndex = 0;
    int x;
    int y;

    public Menus(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addMenu(String name, Runnable function) {
        menus.add(new Menu(x,
                y + menus.size() * (menuMargin + menuHeight), name, function));
        if (menus.size() == 1) {
            setSelectedMenu(0);
        }
    }

    private void setSelectedMenu(int index) {
        index %= menus.size();
        index += menus.size();
        index %= menus.size();
        selectedIndex = index;
        for (Menu menu : menus) {
            menu.setSelected(false);
        }
        menus.get(index).setSelected(true);
    }

    @Override
    public void paint(Graphics2D g2) {
        for (Menu menu : menus) {
            menu.paint(g2);
        }
    }

    public KeyListener getKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setSelectedMenu(selectedIndex + 1);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setSelectedMenu(selectedIndex - 1);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    menus.get(selectedIndex).getFunction().run();
                }
            }
        };
    }
}
