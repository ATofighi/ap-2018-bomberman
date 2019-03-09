package com.atofighi.bomberman.views.server;

import com.atofighi.bomberman.configs.ServerWindowConfiguration;

import javax.swing.*;
import java.awt.*;

import static com.atofighi.bomberman.configs.ServerWindowConfiguration.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Bomberman Game - Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setResizable(false);

        setLocationRelativeTo(null);
    }

    public void setPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        pack();
    }
}
