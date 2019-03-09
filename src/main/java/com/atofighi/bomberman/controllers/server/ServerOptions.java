package com.atofighi.bomberman.controllers.server;

import com.atofighi.bomberman.Server;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.controllers.common.units.MonsterPackage;
import com.atofighi.bomberman.util.Base64ClassLoader;
import com.atofighi.bomberman.util.Base64Helper;
import com.atofighi.bomberman.util.GameStatus;
import com.atofighi.bomberman.util.MonsterPackageClass;
import com.atofighi.bomberman.views.server.ServerOptionsPanel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ServerOptions {
    private Server server;
    private ServerOptionsPanel optionsPanel = new ServerOptionsPanel();

    public ServerOptions(Server server) {
        this.server = server;
        init();
    }

    private void init() {
        optionsPanel.addButton("New Game", (e) -> {
            try {
                int width = Integer.parseInt(JOptionPane.showInputDialog("please enter width:"));
                int height = Integer.parseInt(JOptionPane.showInputDialog("please enter height:"));
                String numberOfMonsterResult = JOptionPane.showInputDialog("please enter number of monsters (optional:");
                int bombermanLimit = Integer.parseInt(JOptionPane.showInputDialog("please enter bomberman limit:"));
                int numberOfMonsters = Math.min(width, height);
                if (!numberOfMonsterResult.equals("")) {
                    numberOfMonsters = Integer.parseInt(numberOfMonsterResult);
                }
                server.newGame(width, height, 1, numberOfMonsters, bombermanLimit);
            } catch (NumberFormatException exception) {

            }
        });
        optionsPanel.addButton("Games", (e) -> {
            server.showGames();
        });
        optionsPanel.addButton("Clients", (e) -> {
            StringBuffer result = new StringBuffer();
            result.append("Clients: \n\n");
            for (ServerProtocol client : server.getClients()) {
                result.append(client.getSocket()).append("  -  ");
                if (client.getCurrentGame() != null) {
                    result.append("Game: #").append(client.getCurrentGame().getId()).append("  -  ");
                }
                if (client.getBomberman() != null) {
                    result.append("Bomberman: ").append(client.getBomberman().getName());
                }
                result.append("\n------------------------------\n");
            }
            JOptionPane.showMessageDialog(optionsPanel, result);
        });

        optionsPanel.addButton("New Monster", (e) -> {
            String className = JOptionPane.showInputDialog("please class full name:");
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    String encodedString = Base64Helper.encode(fileContent);
                    MonsterPackageClass data = new MonsterPackageClass(className, encodedString);
                    Game.addAvailableMonsters(data);
                    server.addAddonMonsterClass(className, data);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(optionsPanel, "ERROR: \n\n" + e1.getMessage());
                }

            }
        });
    }


    public void show() {
        server.getMainFrame().setPanel(optionsPanel);
    }
}
