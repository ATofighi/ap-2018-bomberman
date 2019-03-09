package com.atofighi.bomberman;

import com.atofighi.bomberman.controllers.client.*;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.controllers.common.GameLoader;
import com.atofighi.bomberman.controllers.common.GameMusicPlayer;
import com.atofighi.bomberman.controllers.server.Games;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.views.client.main.Frame;

import javax.swing.*;
import java.io.File;

public class Client {
    private static boolean repaint = false;
    private static final Object repaintLock = new Object();
    private MapSize mapSize;
    private IPSearch ipSearch;
    private GameStartMenu gameStartMenu;
    private GamesSelect games = new GamesSelect(this);
    private Frame mainFrame;
    private ClientProtocol protocol;
    private DBManager dbManager = new DBManager();

    public static void main(String[] args) {
        new Client().run();
    }

    public void run() {
        new GameMusicPlayer().play();

        mainFrame = new Frame();
        mapSize = new MapSize(this);
        ipSearch = new IPSearch(this);
        gameStartMenu = new GameStartMenu(this);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                synchronized (repaintLock) {
                    while (!repaint) {
                        try {
                            repaintLock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    mainFrame.getPanel().repaint();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        setRepaintLoop(true);


        new StartLogo(mainFrame, this::showHome);

    }

    public void setRepaintLoop(boolean repaint) {
        synchronized (repaintLock) {
            Client.repaint = repaint;
            repaintLock.notifyAll();
        }
    }

    public MapSize getMapSize() {
        return mapSize;
    }

    public GameStartMenu getGameStartMenu() {
        return gameStartMenu;
    }

    public Frame getMainFrame() {
        return mainFrame;
    }

    public void newMultiplayerGame() {
        ipSearch.run();
    }

    public void newGame() {
        mapSize.run();
    }

    public void newGame(int w, int h, int numberOfMonsters) {
        newGame(new Game(this, w, h, numberOfMonsters));
    }

    public void newGame(Map map) {
        newGame(new Game(this, map));
    }

    public void newGame(Game game) {
        game.run();
    }

    public void loadGame() {
        loadGame(null);
    }

    private String getSaveLoadType(String title) {
        return (String) JOptionPane.showInputDialog(
                getMainFrame(),
                "Select " + title + " Type",
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"DB", "File"},
                "");
    }

    public void loadGame(Game game) {
        String type = getSaveLoadType("Load");
        GameLoader gameLoader = null;
        if (type != null && type.equals("DB")) {
            String name = (String) JOptionPane.showInputDialog(
                    getMainFrame(),
                    "Select Saved Game",
                    "Saved Games",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    dbManager.getSaves().toArray(),
                    "");
            String json = dbManager.get(name);
            System.out.println(json);
            if (json != null) {
                gameLoader = new GameLoader(json);
            }
        } else {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(mainFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                gameLoader = new GameLoader(file);
            }
        }
        if (game != null) {
            game.close();
        }
        if (gameLoader != null) {
            newGame(gameLoader.getMap());
        }
    }

    public void saveGame(Game game) {
        game.setPaused(true);
        GameLoader gameLoader = new GameLoader(game);
        String type = getSaveLoadType("Save");
        if (type != null && type.equals("DB")) {
            String name = JOptionPane.showInputDialog("Enter name");
            if (name != null) {
                dbManager.insertOrUpdate(name, gameLoader.toJson());
            }
        } else {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(game.getGamePanel());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                gameLoader.save(file);
            }
        }
        game.setPaused(false);
    }

    public void highestScores() { // TODO: complete it
    }

    public void settings() {// TODO: complete it
    }

    public void help() {// TODO: complete it
    }

    public void exit() {
        System.exit(0);
    }

    public void showHome() {
        gameStartMenu.run();
    }

    public ClientProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(ClientProtocol protocol) {
        this.protocol = protocol;
    }

    public void showServerGames() {
        games.run();
    }

    public void viewGame(String id) {
        Map map = protocol.joinGame(id);
        Game game = new Game(this, map, false);
        game.run();
    }

    public void joinGame(String id) {
        Map map = protocol.joinGame(id);
        Game game = new Game(this, map, false);
        String name = JOptionPane.showInputDialog("Enter your name");
        int bombermanId = protocol.newBomberman(name);
        game.setMyBombermanId(bombermanId);
        game.run();
    }
}