package com.atofighi.bomberman.addons;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.controllers.common.units.MapController;
import com.atofighi.bomberman.controllers.common.units.MonsterController;
import com.atofighi.bomberman.controllers.common.units.MonsterPackage;
import com.atofighi.bomberman.models.units.Monster;
import com.atofighi.bomberman.util.Direction;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.google.gson.JsonElement;

import java.awt.*;

public class MonsterAddon extends MonsterPackage {
    private MonsterAddonInterface addon;

    public MonsterAddon(MonsterAddonInterface addon) {
        super(AddonMonster.class, AddonMonsterController.class);
        this.addon = addon;
    }

    public class AddonMonsterPainter extends GamePainter {
        Monster monster;
        MonsterAddonInterface addon;

        public int zIndex() {
            return addon.zIndex();
        }

        public AddonMonsterPainter(MonsterAddonInterface addon, Monster monster) {
            this.addon = addon;
            this.monster = monster;
        }

        public void paint(Graphics2D g2) {
            addon.paint(g2, monster);

        }
    }

    public class AddonMonsterController extends MonsterController {
        private Monster monster;

        public AddonMonsterController(Monster monster, MapController mapController) {
            super(mapController);
            this.monster = monster;
        }

        public Monster get() {
            return this.monster;
        }

        public com.atofighi.bomberman.util.Rectangle getRectangle() {
            return addon.getRectangle(monster);
        }

        public void move() {
            addon.move(get(), mapController.getMap());
            super.move();
        }
    }

    public class AddonMonster extends Monster {
        @Override
        public boolean isGhost() {
            return addon.isGhost();
        }

        @Override
        public int getSpeed() {
            return addon.getSpeed();
        }

        public AddonMonster(int x, int y) {
            super(1, x, y, addon.getSpeed(), addon.getType());
        }

        public int getLegX() {
            return addon.getLegX(this);
        }

        public int getLegY() {
            return addon.getLegY(this);
        }

        public GamePainter makePainter(PaintController.CellType[][] map) {
            return new AddonMonsterPainter(addon, this);
        }

        public MonsterAddonInterface getAddon() {
            return addon;
        }
    }
}
