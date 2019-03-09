package com.atofighi.bomberman;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.controllers.server.*;
import com.atofighi.bomberman.util.GameStatus;
import com.atofighi.bomberman.util.MonsterPackageClass;
import com.atofighi.bomberman.views.server.MainFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private MainFrame mainFrame = new MainFrame();
    ServerListener serverListener;
    ServerOptions serverOptions = new ServerOptions(this);
    Games games = new Games(this);
    List<ServerProtocol> serverProtocols = new ArrayList<>();
    HashMap<String, MonsterPackageClass> addonMonsterClasses = new HashMap<>();

    public static void main(String[] args) {
        new Server().run();
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void run() {
        start();

        mainFrame.setVisible(true);

    }

    private void start() {
        new Start(this).run();
    }

    public void showOptions() {
        serverOptions.show();
    }

    public void createServer(int port) {




        serverListener = new ServerListener(this, port);
        serverListener.start();


    }


    public void showGames() {
        games.show(Game.getAliveGames());
    }

    public List<ServerProtocol> getClients() {
        List<ServerProtocol> result = new ArrayList<>();
        for (ServerProtocol protocol : getServerProtocols()) {
            if (protocol.isAlive()) {
                result.add(protocol);
            }
        }
        return result;
    }

    public void newGame(int width, int height, int level, float numberOfMonsters, int bombermanLimit) {
        width = Math.min(100, Math.max(width, 5));
        height = Math.min(30, Math.max(height, 5));
        Game game = new Game(this, width, height, level, numberOfMonsters, bombermanLimit);
        newGame(game);
    }

    public void newGame(Game game) {
        game.run();
    }

    public List<ServerProtocol> getServerProtocols() {
        return serverProtocols;
    }

    public HashMap<String, MonsterPackageClass> getAddonMonsterClasses() {
        return addonMonsterClasses;
    }

    public void addAddonMonsterClass(String name, MonsterPackageClass content) {
        addonMonsterClasses.put(name, content);
    }
}
