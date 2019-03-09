package com.atofighi.bomberman.views.client.game;

import javax.swing.*;
import java.awt.*;

import static com.atofighi.bomberman.configs.WindowConfiguration.statusPanelWidth;

public class LeftPanel extends JPanel {
    public LeftPanel() {
        setPreferredSize(new Dimension(statusPanelWidth, 0));
        setLayout(new BorderLayout());
    }
}
