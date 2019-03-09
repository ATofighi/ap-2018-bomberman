package com.atofighi.bomberman.views.client.ip_select;

import com.atofighi.bomberman.views.client.game.GamePainter;
import com.atofighi.bomberman.views.util.IntegerField;

import javax.swing.*;
import java.awt.*;

public class IPSearchPanel extends JPanel {
    private JTextField ipField = new JTextField();
    private IntegerField portField = new IntegerField("port", 4);
    private JButton searchButton = new JButton("Search");
    private BackButton backButton = new BackButton();

    public IPSearchPanel() {
        setLayout(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));


        setOpaque(false);
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBounds(100, 150, 400, 140);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
        fieldsPanel.setOpaque(false);
        fieldsPanel.add(new JLabel("Please enter ip range to search:"));
        fieldsPanel.add(Box.createVerticalStrut(3));

        fieldsPanel.add(Box.createVerticalStrut(5));
        fieldsPanel.add(ipField);
        fieldsPanel.add(Box.createVerticalStrut(5));
        fieldsPanel.add(portField);
        fieldsPanel.add(Box.createVerticalStrut(3));
        fieldsPanel.add(searchButton);
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

    public JTextField getIpField() {
        return ipField;
    }

    public IntegerField getPortField() {
        return portField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public BackButton getBackButton() {
        return backButton;
    }
}
