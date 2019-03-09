package com.atofighi.bomberman.controllers.server;

import com.atofighi.bomberman.Server;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.util.GameStatus;
import com.atofighi.bomberman.views.server.GamePanel;
import com.atofighi.bomberman.views.server.GamesPanel;
import com.atofighi.bomberman.views.server.ServerOptionsPanel;

import javax.swing.*;
import java.util.List;

public class Games {
    private Server server;
    private GamesPanel gamesPanel = new GamesPanel();
    private JButton backButton = new JButton("back");

    public Games(Server server) {
        this.server = server;
        init();
    }

    private void init() {
        backButton.addActionListener((e) -> {
            server.showOptions();
        });
    }


    public void show(List<GameStatus> games) {
        gamesPanel.removeAll();
        gamesPanel.add(backButton);
        for (GameStatus gameStatus : games) {
            GamePanel gamePanel = new GamePanel(gameStatus);
            gamePanel.getKillButton().addActionListener((e) -> {
                Game.getGame(gameStatus.id).close();
                server.showGames();
            });

            gamesPanel.add(gamePanel);
        }
        server.getMainFrame().setPanel(gamesPanel);
    }
}
