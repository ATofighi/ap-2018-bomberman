package com.atofighi.bomberman.views.server;

import com.atofighi.bomberman.configs.ServerWindowConfiguration;
import com.atofighi.bomberman.views.util.IntegerField;

import javax.swing.*;
import java.awt.*;

public class NewServerPanel extends JPanel {
    public IntegerField port;
    public JButton submitButton;
    public NewServerPanel() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(new JLabel("Enter port to create a new server:"));
        port = new IntegerField("Server Port", 10);
        port.setMaximumSize(new Dimension(300, 30));
        add(port);
        submitButton = new JButton("Make it!");
        add(submitButton);
    }

    public IntegerField getPort() {
        return port;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }
}
