package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.Client;
import com.atofighi.bomberman.util.GameStatus;
import com.atofighi.bomberman.views.client.games.GameRow;
import com.atofighi.bomberman.views.client.games.GamesPanel;
import com.atofighi.bomberman.views.client.ip_select.IPSearchPanel;

import javax.swing.*;
import java.util.List;

public class GamesSelect {
    private Client app;
    private GamesPanel gamesPanel = new GamesPanel();

    public GamesSelect(Client app) {
        this.app = app;

        gamesPanel.getBackButton().addActionListener((e) -> app.newMultiplayerGame());
        gamesPanel.getNewGameButton().addActionListener((e) -> {
            try {
                int width = Integer.parseInt(JOptionPane.showInputDialog("please enter width:"));
                int height = Integer.parseInt(JOptionPane.showInputDialog("please enter height:"));
                String numberOfMonsterResult = JOptionPane.showInputDialog("please enter number of monsters (optional:");
                int bombermanLimit = Integer.parseInt(JOptionPane.showInputDialog("please enter bomberman limit:"));
                int numberOfMonsters = Math.min(width, height);
                if (!numberOfMonsterResult.equals("")) {
                    numberOfMonsters = Integer.parseInt(numberOfMonsterResult);
                }
                app.getProtocol().newGame(width, height, numberOfMonsters, bombermanLimit);
                run();
            } catch (NumberFormatException exception) {

            }
        });
    }

    public void run() {
        List<GameStatus> games = app.getProtocol().getGames();
        gamesPanel.getTable().removeAll();
        int id = 0;
        for (GameStatus game : games) {
            id++;
            GameRow gameRow = new GameRow(id + "", game.bombermans + "/" + game.bombermanLimit,
                    game.bombermans >= game.bombermanLimit);
            gameRow.getViewButton().addActionListener((e) -> {
                app.viewGame(game.id);
            });
            gameRow.getJoinButton().addActionListener((e) -> {
                app.joinGame(game.id);
            });
            gamesPanel.getTable().add(gameRow);
        }
        app.getMainFrame().setPanel(gamesPanel);
    }

}
