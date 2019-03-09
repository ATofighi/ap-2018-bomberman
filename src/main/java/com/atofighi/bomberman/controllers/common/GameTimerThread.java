package com.atofighi.bomberman.controllers.common;

public class GameTimerThread extends Thread {
    private Game game;

    public GameTimerThread(Game game) {
        this.game = game;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (game.isPaused())
                    continue;
                Thread.sleep(1);
                Game.setCurrentTime(Game.getCurrentTime() + 1);
            }
        } catch (Exception e) {
        }
    }
}
