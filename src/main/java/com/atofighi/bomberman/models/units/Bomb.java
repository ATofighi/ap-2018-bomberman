package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.BombPainter;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.game.SimpleMonsterPainter;

public class Bomb extends Unit {
    private int destroyAt;
    private int range;
    private int ownerId;
    private int destroyRange[] = new int[]{GameConfiguration.defaultBombRange,
            GameConfiguration.defaultBombRange, GameConfiguration.defaultBombRange,
            GameConfiguration.defaultBombRange};

    public Bomb(Bomberman owner, int x, int y, int aliveTime) {
        super(x, y);
        this.ownerId = owner.getId();
        this.range = owner.getBombRange();
        if (aliveTime == -1) {
            this.destroyAt = Integer.MAX_VALUE;
        } else {
            this.destroyAt = Game.getCurrentTime() + aliveTime;
        }
    }


    public int getDestroyAt() {
        return destroyAt;
    }


    public void setDestroyAt(int destroyAt) {
        this.destroyAt = destroyAt;
    }


    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public String toString() {
        return "Bomb{" +
                ", destroyAt=" + destroyAt +
                ", range=" + range +
                '}';
    }


    @Override
    public GamePainter makePainter(PaintController.CellType[][] map) {
        return new BombPainter(this);
    }

    public int[] getDestroyRange() {
        return destroyRange;
    }

    public void setDestroyRange(int[] destroyRange) {
        this.destroyRange = destroyRange;
    }
}
