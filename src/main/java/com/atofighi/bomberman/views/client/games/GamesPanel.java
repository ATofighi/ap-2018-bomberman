package com.atofighi.bomberman.views.client.games;

import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.util.IntegerField;

import javax.swing.*;
import java.awt.*;

public class GamesPanel extends JPanel {
    private BackButton backButton = new BackButton();
    private JButton newGameButton = new JButton("New Game");
    private JPanel table = new JPanel();

    public GamesPanel() {
        setLayout(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        setOpaque(false);
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBounds(100, 150, 400, 140);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
        fieldsPanel.setOpaque(false);
        fieldsPanel.add(backButton);
        fieldsPanel.add(Box.createVerticalStrut(2));
        fieldsPanel.add(newGameButton);
        fieldsPanel.add(Box.createVerticalStrut(3));
        table.setOpaque(false);
        table.setLayout(new BoxLayout(table, BoxLayout.PAGE_AXIS));
        fieldsPanel.add(table);
        add(fieldsPanel);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(GamePainter.getImage("mainMenu.png"), 0, 0, null);
        super.paintComponent(g);
    }

    public BackButton getBackButton() {
        return backButton;
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JPanel getTable() {
        return table;
    }
}
