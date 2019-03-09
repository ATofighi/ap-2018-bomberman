package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.views.util.Paintable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class GamePainter implements Paintable {

    private static Map<String, BufferedImage> imageCache = new HashMap<>();

    public int zIndex() {
        return 1;
    }

    public static BufferedImage getImage(String fileName) {
        if (imageCache.get(fileName) == null) {
            try {
                URL imageURL = GamePainter.class.getClassLoader().getResource(fileName);
                if (imageURL != null) {
                    imageCache.put(fileName, ImageIO.read(imageURL));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageCache.get(fileName);
    }
}
