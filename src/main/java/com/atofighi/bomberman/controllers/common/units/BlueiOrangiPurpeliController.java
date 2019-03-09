package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.*;
import com.atofighi.bomberman.util.Direction;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.views.client.game.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BlueiOrangiPurpeliController extends SimpleMonsterController {
    private Monster monster;
    private int lastRandomMoves = -100000;

    public BlueiOrangiPurpeliController(Monster monster, MapController mapController) {
        super(mapController);
        this.monster = monster;
    }

    public Monster get() {
        return monster;
    }


    Bomberman target = null;

    @Override
    public void move() {
        synchronized (Game.unitsLock) {
            if (target == null || !target.isAlive()) {
                target = mapController.getBombermans()
                        .get((int) (Math.random() * mapController.getBombermans().size())).get();
            }
            if (isTimeOfMove()) {
                monster.setInMove(true);
                if (target == null || !target.isAlive() || Game.getCurrentTime() - lastRandomMoves < 5000) {
                    randomMove(500);
                } else {
                    int distance = distance(Arrays.stream(Direction.values())
                            .min(this::distanceCompare).get());
                    List<Direction> directions = Arrays.stream(Direction.values())
                            .filter(this::canMoveFilter).filter((d) -> this.distance(d) == distance)
                            .collect(Collectors.toList());
                    if (directions.size() != 0) {
                        get().setDirection(directions.get((int) (Math.random() * directions.size())));
                    } else {
                        lastRandomMoves = Game.getCurrentTime();
                    }
                }
                super.move();
            }
        }
    }


    protected int distanceCompare(Direction d1, Direction d2) {
        return distance(d1) - distance(d2);
    }

    protected int distance(Direction d) {
        return distance(get().getRealX() + d.dx(), get().getRealY() + d.dy());
    }

    protected int distance(int x, int y) {
        return Math.abs(x - target.getRealX()) + Math.abs(y - target.getRealY());
    }

    protected boolean canMoveFilter(Direction d) {
        Rectangle monsterRectangle = getRectangle();
        monsterRectangle.setX(monsterRectangle.getX() + d.dx());
        monsterRectangle.setY(monsterRectangle.getY() + d.dy());
        if (!get().isGhost()) {
            return !needRollback(get().getX(), get().getY(), monsterRectangle, getRectangle());
        } else {
            return !needRollback(get().getX(), get().getY(), monsterRectangle, getRectangle(), BombController.class);
        }

    }
}
