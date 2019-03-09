package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.*;
import com.atofighi.bomberman.util.Cell;
import com.atofighi.bomberman.views.client.game.MapPainter;

import java.util.*;
import java.util.stream.Collectors;
import com.atofighi.bomberman.util.Timer;

public class MapController {
    private Map map;
    private List<BombermanController> bombermans = new ArrayList<>(0);
    private List<BombController> bombs = new ArrayList<>(0);
    private List<WallController> walls = new ArrayList<>(0);
    private List<StoneController> stones = new ArrayList<>(0);
    private List<MonsterController> monsters = new ArrayList<>(0);
    private List<PowerUpController> powerUps = new ArrayList<>(0);
    private DoorController door;
    private Set<UnitController>[][] unitsArray;

    public MapController(Map map) {
        this.map = map;
        //noinspection unchecked
        unitsArray = new Set[map.getWidth()][map.getHeight()];
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                unitsArray[i][j] = new HashSet<>();
            }
        }


        for (Bomberman bomberman : map.getBombermans()) {
            BombermanController bombermanController = addBomberman(bomberman, false);
            if (Game.getCurrentTime() - bomberman.getLastDeadTime() < GameConfiguration.refreshTime) {
                bombermanController.setRefreshTimer(new Timer(GameConfiguration.refreshTime -
                        (Game.getCurrentTime() - bomberman.getLastDeadTime())
                        , bombermanController::refresh));
                bombermanController.getRefreshTimer().start();
            }
        }

        for (Bomb bomb : map.getBombs()) {
            addBomb(bomb, false);
        }
        for (Wall wall : map.getWalls()) {
            addWall(wall, false);
        }
        for (Stone stone : map.getStones()) {
            addStone(stone, false);
        }
        for (Monster monster : map.getMonsters()) {
            addMonster(monster, false);
        }
        for (PowerUp powerUp : map.getPowerUps()) {
            addPowerUp(powerUp, false);
        }
        if (map.getDoor() != null) {
            addDoor(map.getDoor());
        }
        if (map.getStones().size() == 0) {
            for (int i = 0; i < map.getWidth(); i++) {
                addStone(i, 0);
                addStone(i, map.getHeight() - 1);
            }
            for (int i = 1; i < map.getHeight() - 1; i++) {
                addStone(0, i);
                addStone(map.getWidth() - 1, i);
            }
            for (int i = 2; i < map.getWidth() - 1; i += 2) {
                for (int j = 2; j < map.getHeight() - 1; j += 2) {
                    addStone(i, j);
                }
            }

        }
    }

    public Map getMap() {
        return map;
    }

    public void addRandomWalls() {
        //synchronized (Game.unitsLock) {
        int[][] map = new int[this.map.getWidth()][this.map.getHeight()];

        for (Stone stone : this.map.getStones()) {
            map[stone.getX()][stone.getY()] = -1;
        }
        for (Monster monster : this.map.getMonsters()) {
            map[monster.getX()][monster.getY()] = -1;
        }
        map[1][1] = map[2][1] = map[1][2] = -1;
        for (int i = 0; i < this.map.getWidth(); i++) {
            for (int j = 0; j < this.map.getHeight(); j++) {
                if (map[i][j] != -1 && Math.random() < 0.7) {
                    addWall(i, j);
                }
            }
        }
        //}
    }

    public BombermanController addBomberman(Bomberman bomberman) {
        return addBomberman(bomberman, true);
    }

    public BombermanController addBomberman(Bomberman bomberman, boolean addToMap) {
        synchronized (Game.unitsLock) {
            destroy(null, bomberman.getX(), bomberman.getY());
            BombermanController bombermanController = new BombermanController(bomberman, this);
            bombermans.add(bombermanController);
            if (addToMap) {
                map.addBomberman(bomberman);
            }
            return bombermanController;
        }
    }

    public void addBomb(Bomb bomb) {
        synchronized (Game.unitsLock) {
            addBomb(bomb, true);
        }
    }

    public void addBomb(Bomb bomb, boolean addToMap) {
        synchronized (Game.unitsLock) {
            BombController bombController = new BombController(bomb, this);
            bombs.add(bombController);
            unitsArray[bomb.getX()][bomb.getY()].add(bombController);
            if (addToMap) {
                map.addBomb(bomb);
            }
        }
    }

    public void addWall(int x, int y) {
        //synchronized (Game.unitsLock) {
        addWall(new Wall(x, y), true);
        //}
    }

    public void addWall(Wall wall, boolean addToMap) {
        //synchronized (Game.unitsLock) {
        WallController wallController = new WallController(wall, this);
        walls.add(wallController);
        unitsArray[wall.getX()][wall.getY()].add(wallController);
        if (addToMap) {
            map.addWall(wall);
        }
        //}
    }

    private void addMonster(Monster monster, boolean addToMap) {
        for (MonsterPackage monsterPackage : Game.getAvailableMonsters()) {
            if (monster.getClass() == monsterPackage.getMonsterClass()) {
                MonsterController monsterController = monsterPackage.newController(monster, this);
                monsters.add(monsterController);
                unitsArray[monster.getX()][monster.getY()].add(monsterController);
                if (addToMap) {
                    map.addMonster(monster);
                }
                break;
            }
        }
    }

    private void addStone(int x, int y) {
        //synchronized (Game.unitsLock) {
        addStone(new Stone(x, y), true);
        //}
    }

    private void addStone(Stone stone, boolean addToMap) {
        //synchronized (Game.unitsLock) {
        StoneController stoneController = new StoneController(stone, this);
        stones.add(stoneController);
        unitsArray[stone.getX()][stone.getY()].add(stoneController);
        if (addToMap) {
            map.addStone(stone);
        }
        //}
    }

    public List<BombermanController> getBombermans() {
        return bombermans;
    }

    public List<BombController> getBombs() {
        return bombs;
    }

    public List<WallController> getWalls() {
        return walls;
    }

    public List<StoneController> getStones() {
        return stones;
    }

    public List<MonsterController> getMonsters() {
        return monsters;
    }

    public Set<UnitController> get(int x, int y) {
        /*List<UnitController> result = new ArrayList<>();
        List[] arrs = new List[]{bombermans, stones, walls, bombs, monsters};
        for (List<UnitController> arr : arrs) {
            for (UnitController controller : arr) {
                if (controller.get().getY() == y && controller.get().getX() == x) {
                    result.add(controller);
                }
            }

        }*/
        synchronized (Game.unitsLock) {
            try {
                return unitsArray[x][y];
            } catch (ArrayIndexOutOfBoundsException ex) {
                return new HashSet<>();
            }
        }
    }

    public void destroy(Bomberman destroyer, int x, int y) {
        synchronized (Game.unitsLock) {
            List<UnitController> unitsToDestroy = new ArrayList<>();
            for (UnitController unit : get(x, y)) {
                if (unit instanceof WallController) {
                } else {
                    unitsToDestroy.add(unit);
                }
            }
            for (UnitController unit : get(x, y)) {
                if (unit instanceof WallController) {
                    unitsToDestroy.add(unit);
                }
            }
            for (UnitController unit : unitsToDestroy) {
                unit.destroy(destroyer);
            }
        }

    }

    public void addRandomMonsters(float numberOfMonsters) {
        List<Cell> freeCells = getFreeCells();
        for (int i = 0; i < Math.min(freeCells.size(), numberOfMonsters); i++) {
            addRandomMonster(freeCells.get(i).x, freeCells.get(i).y);
        }
        if (numberOfMonsters - Math.min(freeCells.size(), numberOfMonsters) >= 1) {
            addRandomMonsters(numberOfMonsters - Math.min(freeCells.size(), numberOfMonsters));
        }
    }

    public List<Cell> getFreeCells() {
        List<Cell> freeCells = new ArrayList<>();
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (get(i, j).stream()
                        .noneMatch(unit -> unit instanceof WallController || unit instanceof StoneController)) {
                    freeCells.add(new Cell(i, j));
                }
            }
        }
        Collections.shuffle(freeCells);
        return freeCells;
    }

    public void addRandomMonster(int x, int y) {
        synchronized (Game.unitsLock) {
            List<MonsterPackage> monsterPackages = new ArrayList<>();
            for (MonsterPackage monsterPackage : Game.getAvailableMonsters()) {
                if (monsterPackage.getLevel() <= map.getLevel()) {
                    monsterPackages.add(monsterPackage);
                }
            }
            int rnd = (int) (Math.random() * monsterPackages.size());
            addMonster(monsterPackages.get(rnd).newMonster(x, y), true);
        }
    }

    public void addPowerUps() {
        List<Wall> walls = new ArrayList<>(map.getWalls()).stream().filter(w ->
                w.getX() != door.get().getX() || w.getY() != door.get().getY()).collect(Collectors.toList());
        Collections.shuffle(walls);
        for (int i = 0; i < Math.min(2 * monsters.size(), (walls.size() + 1) / 3); i++) {
            addPowerUp(new PowerUp(walls.get(i).getX(), walls.get(i).getY(),
                    PowerUp.Type.random(), PowerUp.Name.random()));
        }
    }

    private void addPowerUp(PowerUp powerUp) {
        addPowerUp(powerUp, true);
    }

    private void addPowerUp(PowerUp powerUp, boolean addToMap) {
        synchronized (Game.unitsLock) {
            PowerUpController powerUpController = new PowerUpController(this, powerUp);
            powerUps.add(powerUpController);
            unitsArray[powerUp.getX()][powerUp.getY()].add(powerUpController);
            if (addToMap) {
                map.addPowerUp(powerUp);
            }
        }
    }

    public List<PowerUpController> getPowerUps() {
        return powerUps;
    }

    public DoorController getDoor() {
        return door;
    }

    public void addDoor(Door doorUnit) {
        door = new DoorController(this, doorUnit);
    }

    public void addDoor() {
        if (door == null) {
            Wall wall = map.getWalls().get((int) (Math.random() * map.getWalls().size()));
            Door doorUnit = new Door(wall.getX(), wall.getY());
            map.addDoor(doorUnit);
            addDoor(doorUnit);
            unitsArray[doorUnit.getX()][doorUnit.getY()].add(door);

        }
    }

    public Bomberman getBombermanById(int id) {
        for (BombermanController bombermanController : bombermans) {
            if (bombermanController.get().getId() == id) {
                return bombermanController.get();
            }
        }
        return null;
    }
}
