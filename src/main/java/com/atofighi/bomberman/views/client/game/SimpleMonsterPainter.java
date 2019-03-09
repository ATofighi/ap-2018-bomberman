package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Monster;

import java.awt.*;

public class SimpleMonsterPainter extends MovableUnitPainter {
    private Monster monster;
    private String name;

    public SimpleMonsterPainter(String name, Monster monster) {
        super();
        this.name = name;
        this.monster = monster;

    }

    public void paint(Graphics2D g2) {
        int currentAnimation;
        if (monster.isAlive()) {
            if (!monster.isInMove()) {
                currentAnimation = 0;
            } else {
                currentAnimation = (Game.getCurrentTime() / 200) % 4;
            }
            g2.drawImage(GamePainter.getImage(name + "/" + monster.getDirection() +
                            "/" + (currentAnimation + 1) + ".png"),
                    monster.getRealX(), monster.getRealY(), null);
        }
    }

}
