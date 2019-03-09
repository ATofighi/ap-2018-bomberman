package com.atofighi.bomberman.views.client.start;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class LogoViewer extends JPanel {
    private long startTime;
    public final int timeShow = 3000;
    private static transient BufferedImage image = null;

    public LogoViewer() {
        startTime = new Date().getTime();
        if (image == null) {
            try {
                URL imageURL = getClass().getClassLoader().getResource("startingPage.png");
                if (imageURL != null) {
                    image = ImageIO.read(imageURL);
                }
            } catch (IOException e) {
                System.err.println("failed to load image");
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        long currentTime = new Date().getTime();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.white);
        Composite oldComposite = g2.getComposite();
        float alpha = 1;
        if (currentTime - startTime < timeShow) {
            alpha = 1.0f * (currentTime - startTime) / timeShow;
        } else if (currentTime - startTime > 3 * timeShow) {
            alpha = 0;
        } else if (currentTime - startTime > 2 * timeShow) {
            alpha = 1 - 1.0f * (currentTime - startTime - 2 * timeShow) / timeShow;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.drawImage(image, 0, 0, null);
        g2.setComposite(oldComposite);
    }
}
