package com.atofighi.bomberman.controllers.server;

import com.atofighi.bomberman.Server;
import com.atofighi.bomberman.controllers.common.GameLoader;
import com.atofighi.bomberman.controllers.common.Protocol;
import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.util.Message;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.util.MonsterPackageClass;

import java.io.IOException;
import java.net.Socket;

public class ServerProtocol extends Protocol {
    private Server server;
    private Bomberman bomberman = null;
    private Game currentGame = null;

    public ServerProtocol(Server server, Socket socket) throws IOException {
        super(socket);
        this.server = server;
    }

    public void run() {
        main:
        while (!Thread.interrupted() && socket.isConnected()) {
            while (currentGame != null && !currentGame.isAlive() && currentGame.getNextGame() != null) {
                currentGame = currentGame.getNextGame();
            }
            Message message = getMessage();
            //System.err.println(message);
            switch (message.getType()) {
                case NULL:
                    break;
                case ERROR:
                    // ?
                    break;
                case MAP:
                    if (currentGame == null) {
                        sendMessage(new Message(message.getId(), Message.Type.ERROR, "no-game"));
                    } else {
                        sendMap(message.getId());
                    }
                    break;
                case JOIN:
                    currentGame = Game.getGame(message.getContent());
                    if (currentGame == null) {
                        sendMessage(new Message(message.getId(), Message.Type.ERROR, "no-game"));
                    } else {
                        sendMap(message.getId());
                    }
                    break;
                case NEW_BOMBERMAN:
                    if (currentGame == null) {
                        sendMessage(new Message(message.getId(), Message.Type.ERROR, "no-game"));
                    } else if (currentGame.getMap().getBombermans().size() >= currentGame.getBombermanLimit()) {
                        sendMessage(new Message(message.getId(), Message.Type.ERROR, "game-is-full"));
                    } else {
                        bomberman = currentGame.newBomberman(message.getContent());
                        sendMessage(new Message(message.getId(), Message.Type.JOINED, bomberman.getId() + ""));
                    }
                    break;
                case NEW_GAME:
                    String[] values = message.getContent().split(" ");
                    int width = Integer.parseInt(values[0]);
                    int height = Integer.parseInt(values[1]);
                    int numberOfMonsters = Integer.parseInt(values[2]);
                    int bombermanLimit = Integer.parseInt(values[3]);
                    server.newGame(width, height, 1, numberOfMonsters, bombermanLimit);
                    sendMessage(new Message(message.getId(), Message.Type.LIST,
                            gson.create().toJson(Game.getAliveGames())));
                    break;
                case ACTION:
                    if (currentGame == null) {
                        sendMessage(new Message(message.getId(), Message.Type.ERROR, "no-game"));
                    } else if (bomberman == null) {
                        sendMessage(new Message(message.getId(), Message.Type.ERROR, "no-bomberman"));
                    } else {
                        currentGame.performAction(bomberman,
                                gson.create().fromJson(message.getContent(), Game.Action.class));
                        sendMap(message.getId());
                    }
                    break;
                case LIST:
                    sendMessage(new Message(message.getId(), Message.Type.LIST,
                            gson.create().toJson(Game.getAliveGames())));
                    break;
                case JOINED:
                    break;
                case TEST:
                    sendMessage(new Message(message.getId(), Message.Type.TEST, "ok"));
                    break main;
                case MONSTER:
                    MonsterPackageClass data = server.getAddonMonsterClasses().get(message.getContent());
                    if (data == null) {
                        sendMessage(new Message(message.getId(), Message.Type.ERROR, "no-monster"));
                    } else {
                        sendMessage(new Message(message.getId(), Message.Type.MONSTER, gson.create()
                                .toJson(data)));
                    }
                    break;
                case CHAT:
                    boolean messageSent = false;
                    if (currentGame == null) {
                        messageSent = true;
                        sendMessage(new Message(message.getId(), Message.Type.ERROR, "no-game"));
                    } else {
                        if (!message.getContent().equals("")) {
                            if (bomberman == null) {
                                messageSent = true;
                                sendMessage(new Message(message.getId(), Message.Type.ERROR, "no-bomberman"));
                            } else {
                                currentGame.addChat(bomberman, message.getContent());
                            }
                        }
                    }
                    if (!messageSent) {
                        sendMessage(new Message(message.getId(), Message.Type.CHAT, currentGame.getChats()));
                    }
                    break;
            }
        }
    }

    private void sendMap(String id) {
        sendMessage(new Message(id, Message.Type.MAP, GameLoader.getJson(currentGame)));
    }

    @Override
    public String toString() {
        return "ServerProtocol{" +
                "socket='" + socket + '\'' +
                ", bomberman=" + bomberman.getName() +
                ", currentGame=" + currentGame.getId() +
                '}';
    }

    public Bomberman getBomberman() {
        return bomberman;
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}
