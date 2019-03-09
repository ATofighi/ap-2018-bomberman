//package ir.atofighi.bomberman;
//
//import com.atofighi.bomberman.addons.MonsterAddonInterface;
//import com.atofighi.bomberman.configs.GameConfiguration;
//import com.atofighi.bomberman.models.Map;
//import com.atofighi.bomberman.models.units.Monster;
//import com.atofighi.bomberman.util.Direction;
//import com.atofighi.bomberman.util.Rectangle;
//
//import java.awt.*;
//
//public class BallMonster implements MonsterAddonInterface {
//
//
//    @Override
//    public boolean isGhost() {
//        return true;
//    }
//
//    @Override
//    public int getLegX(Monster monster) {
//        return monster.getRealX() + 32;
//    }
//
//    @Override
//    public int getLegY(Monster monster) {
//        return monster.getRealY() + 32;
//    }
//
//
//    public Rectangle getRectangle(Monster monster) {
//        return new Rectangle(monster.getRealX(), monster.getRealY(), 64, 64);
//    }
//
//    @Override
//    public void move(Monster that, Map map) {
//        that.setInMove(true);
//        if (that.getDirection() == Direction.UP || that.getDirection() == Direction.DOWN
//                || that.getDirection() == null) {
//            that.setDirection(Direction.RIGHT);
//        }
//        if (that.getX() == 1) {
//            that.setDirection(Direction.RIGHT);
//        } else if (that.getX() == map.getWidth() - 2) {
//            that.setDirection(Direction.LEFT);
//        }
//    }
//
//    @Override
//    public int zIndex() {
//        return 5;
//    }
//
//    @Override
//    public void paint(Graphics2D g2, Monster monster) {
//        if (monster.isAlive()) {
//            g2.setColor(new Color(244, 59, 185, 200));
//            g2.fillOval(monster.getRealX(), monster.getRealY(), 64, 64);
//        }
//    }
//
//    @Override
//    public int getSpeed() {
//        return GameConfiguration.defaultBombRange;
//    }
//
//    @Override
//    public int getLevel() {
//        return 1;
//    }
//
//    @Override
//    public String getType() {
//        return "ir.atofighi.bomberman.BallMonster";
//    }
//}