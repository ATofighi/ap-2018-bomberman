package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Bomberman;

import java.awt.*;

public class BombermanPainter extends MovableUnitPainter {

    private Bomberman bomberman;

    public BombermanPainter(Bomberman bomberman) {
        super();
        this.bomberman = bomberman;
    }

    public void paint(Graphics2D g2) {
        int width = BoardConfiguration.cellSize;
        Composite oldComposite = g2.getComposite();
        int currentAnimation;
        if (bomberman.isAlive()) {
            if (bomberman.isInMove()) {
                currentAnimation = (Game.getCurrentTime() / 50) % 8;
            } else {
                currentAnimation = 0;
            }
            {
                if (Game.getCurrentTime() - bomberman.getLastShieldTime() < GameConfiguration.shieldTime) {
                    g2.setColor(new Color(255, 255, 255, 200));
                    g2.fillOval(bomberman.getRealX() - 15, bomberman.getRealY() - 30,
                            96, 96);
                }
                if (bomberman.isGhost()) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                            0.7F));
                }

                Rectangle nameBounds = g2.getFont().getStringBounds(bomberman.getName(),
                        g2.getFontRenderContext()).getBounds();
                g2.setColor(new Color(0, 0, 0, 0.5f));
                g2.fillRect(bomberman.getRealX() + width / 2 - (nameBounds.width / 2) - 3,
                        bomberman.getRealY() - nameBounds.height - 4 - 27,
                        nameBounds.width + 6, nameBounds.height + 6);
                g2.setColor(Color.decode("#f7f7f7"));
                g2.drawString(bomberman.getName(), bomberman.getRealX() + width / 2 - (nameBounds.width / 2),
                        bomberman.getRealY() - 3 - 27);
            }

            drawBomberman(g2, currentAnimation);
        } else {
            currentAnimation = (Game.getCurrentTime() - bomberman.getLastDeadTime()) / 100 + 1;
            if (currentAnimation >= 1 && currentAnimation < 10) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        1.0F / (currentAnimation * currentAnimation)));

                drawBomberman(g2, currentAnimation);
            }
        }
        g2.setComposite(oldComposite);
    }

    private void drawBomberman(Graphics2D g2, int currentAnimation) {
        g2.drawImage(GamePainter.getImage("bomberman/" + bomberman.getDirection().toString() + "/"
                + (currentAnimation + 1) + ".png"), bomberman.getRealX(), bomberman.getRealY() - 27, null);
    }

    public int zIndex() {
        return 11;
    }
}
