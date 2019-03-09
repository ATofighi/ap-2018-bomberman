package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.Client;
import com.atofighi.bomberman.views.client.start.StartMenuPanel;

public class GameStartMenu {
    private Client app;
    private StartMenuPanel startMenuPanel = new StartMenuPanel();

    public GameStartMenu(Client app) {
        this.app = app;

        addStartGameMenu();
        addStartMultiplayerGameMenu();
        addLoadGameMenu();
        addHighestScoreMenu();
        addSettingsMenu();
        addHelpMenu();
        addExitMenu();

        addMultiplayerHostGame();
        addMultiplayerJoinGame();
        addMultiplayerBack();

    }

    public void run() {
        app.getMainFrame().setPanel(startMenuPanel);
    }


    private void addStartGameMenu() {
        startMenuPanel.getMenus().addMenu("Play Game Alone", () -> {
            app.newGame();
        });
    }

    private void addStartMultiplayerGameMenu() {
        startMenuPanel.getMenus().addMenu("Multi-player Game", () -> {
            //startMenuPanel.setCurrentMenu(1);
            app.newMultiplayerGame();
        });
    }

    private void addLoadGameMenu() {
        startMenuPanel.getMenus().addMenu("Load Saved Game", () -> {
            app.loadGame();
        });
    }

    private void addHighestScoreMenu() {
        startMenuPanel.getMenus().addMenu("Highest Scores", () -> {
            app.highestScores();
        });
    }

    private void addSettingsMenu() {
        startMenuPanel.getMenus().addMenu("Settings", () -> {
            app.settings();
        });
    }

    private void addHelpMenu() {
        startMenuPanel.getMenus().addMenu("Read the Help", () -> {
            app.help();
        });
    }


    private void addExitMenu() {
        startMenuPanel.getMenus().addMenu("Exit Game!", () -> {
            app.exit();
        });
    }


    private void addMultiplayerHostGame() {
        startMenuPanel.getMultiplayerMenu().addMenu("Host a Game", () -> {

        });
    }

    private void addMultiplayerJoinGame() {
        startMenuPanel.getMultiplayerMenu().addMenu("Join to a Game", () -> {

        });
    }


    private void addMultiplayerBack() {
        startMenuPanel.getMultiplayerMenu().addMenu("Back", () -> {
            startMenuPanel.setCurrentMenu(0);
        });
    }

}
