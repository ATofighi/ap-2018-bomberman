package com.atofighi.bomberman.views.server;

import com.atofighi.bomberman.util.GameStatus;
import com.atofighi.bomberman.views.util.IntegerField;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public JButton killButton;

    public GamePanel(GameStatus gameStatus) {
        setLayout(new GridLayout(1, 5));
        add(new JLabel("ID: \n" + gameStatus.id.substring(0, 16)));
        add(new JLabel(gameStatus.aliveTime + ""));
        add(new JLabel(gameStatus.bombermans + "/" + gameStatus.bombermanLimit));
        killButton = new JButton("Kill it!");
        add(killButton);
        setMaximumSize(new Dimension(500, 25));
    }

    public JButton getKillButton() {
        return killButton;
    }

}
