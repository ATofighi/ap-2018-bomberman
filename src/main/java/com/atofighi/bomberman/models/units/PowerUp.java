package com.atofighi.bomberman.models.units;

import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.game.DoorPainter;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.game.PowerUpPainter;

public class PowerUp extends Unit {
    public enum Type {
        INCREMENT, DECREMENT;

        public static Type random() {
            int a = (int) (Math.random() * 2);
            if (a == 0)
                return INCREMENT;
            return DECREMENT;
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public enum Name {
        BOMB_NUMBER, BOMB_RADIUS, CONTROL_BOMB, BOMBERMAN_SPEED, POINTS, GHOST;

        public static Name random() {
            int a = (int) (Math.random() * 6);
            switch (a) {
                case 0:
                    return BOMB_NUMBER;
                case 1:
                    return BOMB_RADIUS;
                case 2:
                    return CONTROL_BOMB;
                case 3:
                    return BOMBERMAN_SPEED;
                case 4:
                    return POINTS;
                default:
                    return GHOST;
            }
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private Type type;
    private Name name;

    public PowerUp(int x, int y, Type type, Name name) {
        super(x, y);
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public Name getName() {
        return name;
    }


    @Override
    public GamePainter makePainter(PaintController.CellType[][] map) {
        return new PowerUpPainter(this);
    }
}
