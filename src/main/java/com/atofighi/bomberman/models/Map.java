package com.atofighi.bomberman.models;

import com.atofighi.bomberman.models.units.*;
import com.atofighi.bomberman.util.RandomString;
import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.client.game.MapPainter;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int width;
    private int height;
    private int currentTime;
    private int unitsAutoIncrementId;
    private int level;
    private List<Bomberman> bombermans = new ArrayList<>(0);
    private List<Bomb> bombs = new ArrayList<>(0);
    private List<Wall> walls = new ArrayList<>(0);
    private List<Stone> stones = new ArrayList<>(0);
    private List<Monster> monsters = new ArrayList<>(0);
    private List<PowerUp> powerUps = new ArrayList<>(0);
    private Door door;
    private String id;
    private boolean winned = false;

    public Map(String id, int level, int width, int height) {
        this.id = id;
        this.level = level;
        this.width = width;
        this.height = height;
    }

    public void addBomberman(Bomberman bomberman) {
        bombermans.add(bomberman);
    }

    public void addStone(Stone stone) {
        stones.add(stone);
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void addDoor(Door door) {
        this.door = door;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Bomberman> getBombermans() {
        return bombermans;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public List<Stone> getStones() {
        return stones;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Door getDoor() {
        return door;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "Map{" +
                "width=" + width +
                ", height=" + height +
                ", bombermans=" + bombermans +
                ", bombs=" + bombs +
                ", walls=" + walls +
                ", stones=" + stones +
                ", monsters=" + monsters +
                ", powerUps=" + powerUps +
                '}';
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUnitsAutoIncrementId() {
        return unitsAutoIncrementId;
    }

    public void setUnitsAutoIncrementId(int unitsAutoIncrementId) {
        this.unitsAutoIncrementId = unitsAutoIncrementId;
    }

    public String getId() {
        return id;
    }

    public GamePainter makePainter() {
        return new MapPainter(this);
    }
}
