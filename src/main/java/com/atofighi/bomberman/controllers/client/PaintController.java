package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.Stone;
import com.atofighi.bomberman.models.units.Unit;
import com.atofighi.bomberman.models.units.Wall;
import com.atofighi.bomberman.views.client.game.GamePainter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PaintController {
    public enum CellType {
        EMPTY, WALL, STONE;
    }

    private List<GamePainter> painters = new ArrayList<>();
    private Map map;
    private CellType[][] simpleMap;

    public PaintController(Map map) {
        this.map = map;
        simpleMap = new CellType[map.getWidth()][map.getHeight()];
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                simpleMap[i][j] = CellType.EMPTY;
            }
        }
        painters.add(map.makePainter());
        List[] unitsCollections = new List[]{
                map.getBombermans(),
                map.getWalls(),
                map.getMonsters(),
                map.getMonsters(),
                map.getStones(),
                map.getBombs(),
                map.getPowerUps(),
                Arrays.stream(new Unit[]{map.getDoor()}).collect(Collectors.toList())

        };

        for (List<Unit> units : unitsCollections) {
            for (Unit unit : units) {
                add(unit);
            }
        }
    }

    public void add(Unit unit) {
        if (unit.isAlive()) {
            if (unit instanceof Wall) {
                simpleMap[unit.getX()][unit.getY()] = CellType.WALL;
            } else if (unit instanceof Stone) {
                simpleMap[unit.getX()][unit.getY()] = CellType.STONE;
            }
        }
        painters.add(unit.makePainter(simpleMap));

    }

    public void paint(Graphics2D g2) {
        painters.stream().sorted(Comparator.comparingInt(GamePainter::zIndex))
                .forEach((painter) -> painter.paint(g2));
    }

    public Map getMap() {
        return map;
    }
}
