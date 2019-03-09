package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.controllers.common.GameLoader;
import com.atofighi.bomberman.controllers.common.Protocol;
import com.atofighi.bomberman.controllers.common.units.MonsterPackage;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.util.GameStatus;
import com.atofighi.bomberman.util.Message;
import com.atofighi.bomberman.util.MonsterPackageClass;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ClientProtocol extends Protocol {
    private String name = null;
    private Bomberman bomberman = null;
    private Game currentGame = null;
    private HashMap<String, Message> unread = new HashMap<>();
    private long lastGetMap = -1000;
    private Map cachedMap;

    public ClientProtocol(Socket socket) throws IOException {
        super(socket);
    }

    private synchronized Message getMessage(String id) {
        do {
            if (unread.get(id) != null) {
                Message result = unread.get(id);
                unread.remove(id);
                return result;
            }
            Message message = getMessage();
            unread.put(message.getId(), message);
        } while (true);
    }

    public boolean test() {
        Message request = new Message(Message.Type.TEST, "");
        sendMessage(request);
        Message response = getMessage(request.getId());
        if (response.getContent().equals("ok")) {
            return true;
        }
        return false;
    }


    public List<GameStatus> getGames() {
        sendMessage(new Message(Message.Type.LIST, ""));
        Message message = getMessage();
        if (message.getType() == Message.Type.LIST) {
            return gson.create().fromJson(message.getContent(), new TypeToken<List<GameStatus>>() {
            }.getType());
        }
        return new ArrayList<>();
    }

    public Map getMap() {
        return getMap(true, "");
    }

    public Map getMap(boolean sendRequest, String id) {
        if (sendRequest) {
            if (lastGetMap + 100 > new Date().getTime()) {
                return cachedMap;
            }
            Message request = new Message(Message.Type.MAP, "");
            sendMessage(request);
            id = request.getId();
        }
        lastGetMap = new Date().getTime();
        Message message = getMessage(id);
        if (message.getType() == Message.Type.MAP) {
            return cachedMap = GameLoader.getMap(this, message.getContent());
        } else {
            System.err.println("NO MAP! " + message);
        }
        return null;
    }

    public Map joinGame(String id) {
        Message message = new Message(Message.Type.JOIN, id);
        sendMessage(message);
        return getMap(false, message.getId());
    }

    public int newBomberman(String name) {
        Message request = new Message(Message.Type.NEW_BOMBERMAN, name);
        sendMessage(request);
        Message message = getMessage(request.getId());
        if (message.getType() == Message.Type.JOINED) {
            return Integer.parseInt(message.getContent());
        }
        return -1;
    }

    public Map doAction(Game.Action action) {
        Message request = new Message(Message.Type.ACTION, gson.create().toJson(action));
        sendMessage(request);
        return getMap(false, request.getId());
    }

    public void newGame(int width, int height, int numberOfMonsters, int bombermanLimit) {
        Message request = new Message(Message.Type.NEW_GAME,
                width + " " + height + " " + numberOfMonsters + " " + bombermanLimit);
        sendMessage(request);
        getMessage(request.getId());
    }

    public void addMonster(String monsterType) {
        Message request = new Message(Message.Type.MONSTER, monsterType);
        sendMessage(request);
        Message response = getMessage(request.getId());
        if (response.getType() == Message.Type.MONSTER) {
            MonsterPackageClass data = gson.create().fromJson(response.getContent(), MonsterPackageClass.class);
            try {
                Game.addAvailableMonsters(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendChat(String message) {
        Message request = new Message(Message.Type.CHAT, message);
        sendMessage(request);
        Message response = getMessage(request.getId());
    }

    public String getChats() {
        Message request = new Message(Message.Type.CHAT, "");
        sendMessage(request);
        Message response = getMessage(request.getId());
        if(response.getType() == Message.Type.CHAT) {
            return response.getContent();
        }
        return null;
    }
}
