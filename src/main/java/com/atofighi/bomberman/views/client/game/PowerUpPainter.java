package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.models.units.PowerUp;

public class PowerUpPainter extends SimpleUnitPainter {

    public PowerUpPainter(PowerUp powerUp) {
        super(powerUp, "powerUps/" + powerUp.getName() + "-" + powerUp.getType() + ".png");

    }
}
