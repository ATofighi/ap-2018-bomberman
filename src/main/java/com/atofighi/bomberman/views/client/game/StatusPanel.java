package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.configs.GameConfiguration;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.models.units.Bomberman;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.atofighi.bomberman.configs.WindowConfiguration.statusPanelWidth;


public class StatusPanel extends JPanel {

    private Game game;

    public StatusPanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(statusPanelWidth, 0));
    }

    private int writeY;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.decode("#42383a"));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(Color.decode("#ffffff"));

        int time = (GameConfiguration.gameTime - (Game.getCurrentTime() - game.getStartTime())) / 1000;
        writeY = 70;
        if (game.myBomberman() != null) {
            int points = game.myBomberman().getPoints() + Math.min(0, time);
            write(g2, "LEVEL", "" + game.getMap().getLevel());
            write(g2, "TIME", "" + Math.max(0, time));
            write(g2, "POINTS", "" + points);

            write(g2, "BOMB RANGE", "" + game.myBomberman().getBombRange());

            write(g2, "BOMB LIMIT", "" + game.myBomberman().getBombLimit());

            write(g2, "SPEED", "" + game.myBomberman().getSpeed());
        }

        if (game.getType() == Game.Type.MULTI_PLAYER_CLIENT) {
            List<BombermanPoint> bombermans = new ArrayList<>();
            for (Bomberman bomberman : game.getMap().getBombermans()) {
                bombermans.add(new BombermanPoint(bomberman.getName(), bomberman.getPoints()));
            }
            bombermans.sort((a, b) -> a.point - b.point);
            StringBuilder list = new StringBuilder();
            int bestPoint = 1000000;
            int rank = 0;
            for (int i = 0; i < bombermans.size(); i++) {
                if (bestPoint != bombermans.get(i).point) {
                    bestPoint = bombermans.get(i).point;
                    rank = i;
                }
                list.append(rank + 1).append(". ").append(bombermans.get(i).name).append("\n");
            }
            write(g2, "SCOREBOARD", list.toString());
        }
    }


    private void write(Graphics2D g2, String title, String value) {
        g2.drawString(title + ":", 20, writeY += 30);
        String[] values = value.split("\n");
        for (String v : values) {
            g2.drawString(v, 80, writeY += 20);
        }
    }
}

class BombermanPoint {
    String name;
    int point;

    public BombermanPoint(String name, int point) {
        this.name = name;
        this.point = point;
    }
}
