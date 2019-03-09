package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.controllers.common.Game;

import javax.swing.*;
import java.awt.*;

import static com.atofighi.bomberman.configs.WindowConfiguration.statusPanelWidth;

public class ChatPanel extends JPanel {
    private JTextArea textArea = new JTextArea();
    private JTextField textField = new JTextField();

    public ChatPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(statusPanelWidth, 200));
        textArea.setRows(15);
        textArea.setEditable(false);
        add(textArea, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getTextField() {
        return textField;
    }
}
