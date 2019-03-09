package com.atofighi.bomberman.controllers.server;

import com.atofighi.bomberman.Server;
import com.atofighi.bomberman.views.server.NewServerPanel;

public class Start {
    private Server server;
    private NewServerPanel newServerPanel = new NewServerPanel();

    public Start(Server server) {
        this.server = server;
    }

    public void run() {
        newServerPanel.getSubmitButton().addActionListener((event) -> {
            server.createServer(newServerPanel.getPort().getValue());
            server.showOptions();
        });
        server.getMainFrame().setPanel(newServerPanel);
    }


}
