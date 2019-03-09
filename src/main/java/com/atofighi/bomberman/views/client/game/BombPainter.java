package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Bomb;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BombPainter extends GamePainter {
    private static transient BufferedImage image = null;
    private static transient BufferedImage[] imageDestroyCenter = null;
    private static transient BufferedImage[] imageDestroyHorizontal = null;
    private static transient BufferedImage[] imageDestroyVertical = null;
    private static transient BufferedImage[][] imageDestroyHead = null;
    private Bomb bomb;

    public BombPainter(Bomb bomb) {
        this.bomb = bomb;
        if (image == null) image = getImage("bomb.png");
        if (imageDestroyCenter == null) {
            imageDestroyCenter = new BufferedImage[]{
                    getImage("bomb-destroy0-center.png"),
                    getImage("bomb-destroy-center.png")
            };
        }

        if (imageDestroyHorizontal == null) {
            imageDestroyHorizontal = new BufferedImage[]{
                    getImage("bomb-destroy0-horizontal.png"),
                    getImage("bomb-destroy-horizontal.png")
            };
        }

        if (imageDestroyVertical == null) {
            imageDestroyVertical = new BufferedImage[]{
                    getImage("bomb-destroy0-vertical.png"),
                    getImage("bomb-destroy-vertical.png")
            };
        }
        if (imageDestroyHead == null) {
            imageDestroyHead = new BufferedImage[4][];
            int i = 0;
            for (String direction : new String[]{"top", "right", "bottom", "left"}) {
                imageDestroyHead[i] = new BufferedImage[]{
                        getImage("bomb-destroy0-head-" + direction + ".png"),
                        getImage("bomb-destroy-head-" + direction + ".png")
                };
                i++;
            }
        }
    }


    public void paint(Graphics2D g2) {
        int width = BoardConfiguration.cellSize;
        int currentAnimation;
        int[] range = bomb.getDestroyRange();
        if (bomb.isAlive()) {
            currentAnimation = (Game.getCurrentTime() / 300) % 5;

            g2.drawImage(image.getSubimage(currentAnimation * width, 0, width, width),
                    bomb.getX() * BoardConfiguration.cellSize,
                    bomb.getY() * BoardConfiguration.cellSize, null);
            int seconds = (int) Math.round((bomb.getDestroyAt() - Game.getCurrentTime()) / 1000.0);
            if (seconds < 10) {

                g2.setColor(Color.decode("#ff0000"));
                g2.drawString("" + seconds,
                        bomb.getX() * width + width - 10,
                        bomb.getY() * width + width - 5);
            }
        } else {
            currentAnimation = (Game.getCurrentTime() - bomb.getDestroyAt()) / 200;
            if (currentAnimation < 2 && currentAnimation >= 0) {
                g2.drawImage(imageDestroyCenter[currentAnimation], bomb.getX() * BoardConfiguration.cellSize,
                        bomb.getY() * BoardConfiguration.cellSize, null);

                for (int i = 1; i < range[0]; i++) {
                    g2.drawImage(imageDestroyVertical[currentAnimation], bomb.getX() * BoardConfiguration.cellSize,
                            (bomb.getY() - i) * BoardConfiguration.cellSize, null);
                }
                g2.drawImage(imageDestroyHead[0][currentAnimation], bomb.getX() * BoardConfiguration.cellSize,
                        (bomb.getY() - range[0]) * BoardConfiguration.cellSize, null);

                for (int i = 1; i < range[2]; i++) {
                    g2.drawImage(imageDestroyVertical[currentAnimation], bomb.getX() * BoardConfiguration.cellSize,
                            (bomb.getY() + i) * BoardConfiguration.cellSize, null);
                }
                g2.drawImage(imageDestroyHead[2][currentAnimation], bomb.getX() * BoardConfiguration.cellSize,
                        (bomb.getY() + range[2]) * BoardConfiguration.cellSize, null);

                for (int i = 1; i < range[1]; i++) {
                    g2.drawImage(imageDestroyHorizontal[currentAnimation], (bomb.getX() + i) * BoardConfiguration.cellSize,
                            bomb.getY() * BoardConfiguration.cellSize, null);
                }
                g2.drawImage(imageDestroyHead[1][currentAnimation], (bomb.getX() + range[1]) * BoardConfiguration.cellSize,
                        bomb.getY() * BoardConfiguration.cellSize, null);

                for (int i = 1; i < range[3]; i++) {
                    g2.drawImage(imageDestroyHorizontal[currentAnimation], (bomb.getX() - i) * BoardConfiguration.cellSize,
                            bomb.getY() * BoardConfiguration.cellSize, null);
                }
                g2.drawImage(imageDestroyHead[3][currentAnimation], (bomb.getX() - range[3]) * BoardConfiguration.cellSize,
                        bomb.getY() * BoardConfiguration.cellSize, null);
            }

        }
    }


    @Override
    public int zIndex() {
        return 4;
    }
}
