package com.atofighi.bomberman.views.client.games;

import com.atofighi.bomberman.util.GameStatus;

import javax.swing.*;
import java.awt.*;

public class GameRow extends JPanel {
    private JButton joinButton;
    private JButton viewButton;

    public GameRow(String id, String capacity, boolean closed) {
        setLayout(new GridLayout(1, 4));
        setOpaque(false);
        setMaximumSize(new Dimension(400, 25));
        JLabel idLabel = new JLabel("#" + id);
        idLabel.setOpaque(false);
        add(idLabel);

        JLabel capacityLabel = new JLabel(capacity);
        capacityLabel.setOpaque(false);
        add(capacityLabel);

        joinButton = new JButton("join");
        if (closed) {
            joinButton.setEnabled(false);
        }
        add(joinButton);

        viewButton = new JButton("view");
        add(viewButton);
    }

    public JButton getJoinButton() {
        return joinButton;
    }

    public JButton getViewButton() {
        return viewButton;
    }
}
