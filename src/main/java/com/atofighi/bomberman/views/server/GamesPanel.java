package com.atofighi.bomberman.views.server;

import com.atofighi.bomberman.util.GameStatus;

import javax.swing.*;
import java.awt.*;

public class GamesPanel extends JPanel {
    public JButton killButton;
    public GamesPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
