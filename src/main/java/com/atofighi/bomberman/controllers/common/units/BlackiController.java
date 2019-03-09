package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Blacki;
import com.atofighi.bomberman.views.client.game.BlackiPainter;

public class BlackiController extends SimpleMonsterController {
    private Blacki blacki;

    public BlackiController(Blacki blacki, MapController mapController) {
        super(mapController);
        this.blacki = blacki;
    }

    public Blacki get() {
        return blacki;
    }



    @Override
    public void move() {
        synchronized (Game.unitsLock) {
            blacki.setInMove(true);
            if (isTimeOfMove()) {
                randomMove(5000);
            }
        }
    }

}
