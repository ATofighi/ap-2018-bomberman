package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.models.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapPainter extends GamePainter {

    private Map map;

    private BufferedImage grassImage;

    public MapPainter(Map map) {
        super();
        this.map = map;
        grassImage = GamePainter.getImage("grass.png");
    }

    public void paint(Graphics2D g2) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                g2.drawImage(grassImage,
                        i * BoardConfiguration.cellSize, j * BoardConfiguration.cellSize, null);
            }
        }
    }

    @Override
    public int zIndex() {
        return -1;
    }
}
