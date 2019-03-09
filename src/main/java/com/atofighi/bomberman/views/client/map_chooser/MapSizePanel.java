package com.atofighi.bomberman.views.client.map_chooser;

import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.util.IntegerField;

import javax.swing.*;
import java.awt.*;

public class MapSizePanel extends JPanel {
    private IntegerField widthField = new IntegerField("Map Width", 2);
    private IntegerField heightField = new IntegerField("Map Height", 2);
    private IntegerField monsterNumberField = new IntegerField("Number of monsters (Optional)", 2);
    private MakeButton makeButton = new MakeButton();
    private BackButton backButton = new BackButton();

    public MapSizePanel() {
        setLayout(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));


        setOpaque(false);
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBounds(100, 150, 400, 150);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
        fieldsPanel.setOpaque(false);
        fieldsPanel.add(new JLabel("Please enter map width and height:"));
        fieldsPanel.add(Box.createVerticalStrut(3));
        JPanel sizePanel = new JPanel();
        sizePanel.setOpaque(false);
        sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.LINE_AXIS));
        sizePanel.add(widthField);
        sizePanel.add(new JLabel("×"));
        sizePanel.add(heightField);
        fieldsPanel.add(sizePanel);

        fieldsPanel.add(Box.createVerticalStrut(5));
        fieldsPanel.add(monsterNumberField);
        fieldsPanel.add(Box.createVerticalStrut(3));
        fieldsPanel.add(makeButton);
        fieldsPanel.add(Box.createVerticalStrut(5));
        fieldsPanel.add(backButton);
        add(fieldsPanel);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(GamePainter.getImage("mainMenu.png"), 0, 0, null);
        super.paintComponent(g);
    }

    public MakeButton getMakeButton() {
        return makeButton;
    }

    public IntegerField getWidthField() {
        return widthField;
    }

    public IntegerField getHeightField() {
        return heightField;
    }

    public BackButton getBackButton() {
        return backButton;
    }


    public IntegerField getMonsterNumberField() {
        return monsterNumberField;
    }
}
