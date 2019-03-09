package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.controllers.common.units.MapController;
import com.atofighi.bomberman.controllers.common.units.StoneController;
import com.atofighi.bomberman.controllers.common.units.UnitController;
import com.atofighi.bomberman.controllers.common.units.WallController;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.Stone;
import com.atofighi.bomberman.models.units.Unit;
import com.atofighi.bomberman.models.units.Wall;

import java.awt.*;

import static com.atofighi.bomberman.configs.BoardConfiguration.cellSize;

public class StoneWallPainter extends GamePainter {
    private String image;
    private PaintController.CellType[][] map;
    private Unit unit;
    private String cache = null;

    public StoneWallPainter(Unit unit, String image, PaintController.CellType[][] map) {
        this.image = image;
        this.map = map;
        this.unit = unit;
    }

    @Override
    public void paint(Graphics2D g2) {
        if (unit.isAlive()) {
            int x = unit.getX();
            int y = unit.getY();
            String sides = "";
            if (cache == null) {
                sides += isWallOrStone(x, y - 1) ? "1" : "0";
                sides += isWallOrStone(x + 1, y) ? "1" : "0";
                sides += isWallOrStone(x, y + 1) ? "1" : "0";
                sides += isWallOrStone(x - 1, y) ? "1" : "0";
                cache = sides;
            } else {
                sides = cache;
            }
            g2.drawImage(GamePainter.getImage(String.format(image, sides)), x * cellSize, y * cellSize, null);
        }
    }

    private boolean isWallOrStone(int x, int y) {
        try {
            PaintController.CellType cellType = map[x][y];
            if (unit instanceof Wall && cellType == PaintController.CellType.WALL)
                return true;
            if (unit instanceof Stone && cellType == PaintController.CellType.STONE)
                return true;
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return false;
    }

    public void clearCache() {
        cache = null;
    }
}
