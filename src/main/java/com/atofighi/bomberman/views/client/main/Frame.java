package com.atofighi.bomberman.views.client.main;

import com.atofighi.bomberman.configs.WindowConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends JFrame {

    private JPanel mainPanel;
    private JPanel panel = new JPanel();

    public Frame() {
        setTitle("Bomberman Game");

        { // Hide Cursor (https://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application)
            // Transparent 16 x 16 pixel cursor image.
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

            // Create a new blank cursor.
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImg, new Point(0, 0), "blank cursor");

            setCursor(blankCursor);
        }

        //setIconImage(); //TODO: set icon for game

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getContentPane().setBackground(WindowConfiguration.backgroundColor);

        mainPanel = new JPanel();
        Box box = new Box(BoxLayout.PAGE_AXIS);
        box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        Box boxHorizontal = new Box(BoxLayout.LINE_AXIS);
        boxHorizontal.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        boxHorizontal.add(Box.createHorizontalGlue());
        boxHorizontal.add(mainPanel);
        boxHorizontal.add(Box.createHorizontalGlue());

        box.add(boxHorizontal);
        box.add(Box.createVerticalGlue());
        mainPanel.setMaximumSize(new Dimension(1024, 768));
        mainPanel.setMinimumSize(new Dimension(1024, 768));
        mainPanel.setPreferredSize(new Dimension(1024, 768));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        add(box);

        setVisible(true);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
        mainPanel.removeAll();
        mainPanel.add(panel);
        panel.requestFocus();
        revalidate();
        repaint();
        panel.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }
}
