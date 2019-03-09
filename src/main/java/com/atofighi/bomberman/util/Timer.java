package com.atofighi.bomberman.util;

public class Timer extends Thread {
    private int time;
    private Runnable runnable;

    public Timer(int time, Runnable runnable) {
        this.time = time;
        this.runnable = runnable;
    }

    public void run() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
        runnable.run();
    }
}
