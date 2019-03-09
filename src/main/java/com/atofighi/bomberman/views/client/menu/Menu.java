package com.atofighi.bomberman.views.client.menu;

import com.atofighi.bomberman.views.util.Paintable;

import java.awt.*;
import java.io.IOException;

import static com.atofighi.bomberman.configs.GameMenuConfiguration.*;

public class Menu implements Paintable {
    private String name;
    private Runnable function;
    private boolean selected = false;
    private int x, y;
    private Font font;

    Menu(int x, int y, String name, Runnable function) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.function = function;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getClassLoader().getResourceAsStream("good-times.ttf"));
            font = font.deriveFont(Font.PLAIN, 20);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics2D g2) {
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON));

        g2.setColor(Color.decode("#9b59b6"));
        int h = menuHeight, w = menuWidth;
        int x = this.x;
        int y = this.y;
        if (selected) {
            g2.setColor(Color.decode("#2ecc71"));
            w += 4;
            h += 4;
            x -= 2;
            y -= 2;
        }
        g2.fillOval(x, y, h, h);
        g2.fillRect(x + h / 2, y, w - h, h);
        g2.fillOval(x + w - h, y, h, h);
        g2.setColor(Color.white);
        g2.setFont(font);
        Rectangle nameBounds = g2.getFont().getStringBounds(name, g2.getFontRenderContext()).getBounds();
        g2.drawString(name, x + (w - nameBounds.width) / 2, y + padding);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Runnable getFunction() {
        return function;
    }
}
