package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.models.units.Unit;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimpleUnitPainter extends GamePainter {
    private BufferedImage image;
    private Unit unit;

    protected SimpleUnitPainter(Unit unit, String fileName) {
        super();
        this.unit = unit;
        image = GamePainter.getImage(fileName);

    }

    public void paint(Graphics2D g2) {
        if (unit.isAlive()) {
            g2.drawImage(image, unit.getX() * BoardConfiguration.cellSize,
                    unit.getY() * BoardConfiguration.cellSize, null);
        }
    }

}
