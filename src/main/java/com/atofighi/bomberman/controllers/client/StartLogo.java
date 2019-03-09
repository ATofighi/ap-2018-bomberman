package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.views.client.main.Frame;
import com.atofighi.bomberman.views.client.start.LogoViewer;
import com.atofighi.bomberman.util.Timer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StartLogo {
    public StartLogo(Frame mainFrame, Runnable thenFunction) {
        LogoViewer logoViewer = new LogoViewer();
        Timer timer = new Timer(logoViewer.timeShow*3, thenFunction::run);
        logoViewer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    timer.interrupt();
                }
            }
        });
        mainFrame.setPanel(logoViewer);
        timer.start();

    }
}
