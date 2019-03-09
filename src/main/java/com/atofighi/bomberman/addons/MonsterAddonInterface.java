package com.atofighi.bomberman.addons;

import com.atofighi.bomberman.controllers.common.units.MonsterController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.Monster;
import com.atofighi.bomberman.util.Direction;
import com.atofighi.bomberman.util.Rectangle;

import java.awt.*;

public interface MonsterAddonInterface {
    boolean isGhost();

    int getSpeed();

    int getLevel();

    // type must be full class name
    String getType();

    int getLegX(Monster monster);

    int getLegY(Monster monster);

    Rectangle getRectangle(Monster monster);

    void move(Monster monster, Map map);

    int zIndex();

    void paint(Graphics2D g2, Monster monster);
}
