package com.atofighi.bomberman.views.server;

import com.atofighi.bomberman.views.util.IntegerField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ServerOptionsPanel extends JPanel {
    int buttonsCount = 0;
    public ServerOptionsPanel() {
        setLayout(new GridLayout(buttonsCount, 1));
    }

    public void addButton(String title, ActionListener actionListener) {
        JButton button = new JButton(title);
        button.addActionListener(actionListener);
        add(button);
        buttonsCount++;
        setLayout(new GridLayout(buttonsCount, 1));
    }
}
