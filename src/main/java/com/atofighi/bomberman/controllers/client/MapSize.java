package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.Client;
import com.atofighi.bomberman.views.client.map_chooser.MapSizePanel;

public class MapSize implements Runnable {
    private Client app;
    private MapSizePanel mapSizePanel = new MapSizePanel();

    public MapSize(Client app) {
        this.app = app;

        mapSizePanel.getMakeButton().addActionListener((e) -> {
            int w = Math.min(100, Math.max(mapSizePanel.getWidthField().getValue(), 5));
            int h = Math.min(30, Math.max(mapSizePanel.getHeightField().getValue(), 5));
            int numberOfMonsters = mapSizePanel.getMonsterNumberField().getValue();
            if (numberOfMonsters == 0) {
                numberOfMonsters = Math.min(w, h);
            }
            app.newGame(w, h, numberOfMonsters);
        });
        mapSizePanel.getBackButton().addActionListener((e) -> app.showHome());
    }

    @Override
    public void run() {
        app.getMainFrame().setPanel(mapSizePanel);
    }

}
