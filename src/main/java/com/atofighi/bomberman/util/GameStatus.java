package com.atofighi.bomberman.util;

public class GameStatus {
    public String id;
    public int aliveTime;
    public int bombermans;
    public int bombermanLimit;

    public GameStatus(String id, int aliveTime, int bombermans, int bombermanLimit) {
        this.id = id;
        this.aliveTime = aliveTime;
        this.bombermans = bombermans;
        this.bombermanLimit = bombermanLimit;
    }

    @Override
    public String toString() {
        return "GameStatus{" +
                "id='" + id + '\'' +
                ", aliveTime=" + aliveTime +
                ", bombermans=" + bombermans +
                ", bombermanLimit=" + bombermanLimit +
                '}';
    }
}
