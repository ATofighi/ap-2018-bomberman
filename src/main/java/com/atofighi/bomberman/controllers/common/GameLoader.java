package com.atofighi.bomberman.controllers.common;

import com.atofighi.bomberman.controllers.client.ClientProtocol;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.Monster;
import com.atofighi.bomberman.models.units.Unit;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

public class GameLoader {

    private Map map;
    private GsonBuilder gson = new GsonBuilder();

    private GameLoader(ClientProtocol protocol) {
        gson.registerTypeAdapter(Monster.class, new MonsterAdapter(protocol));
    }


    private GameLoader() {
        this((ClientProtocol) null);
    }

    public GameLoader(Game game) {
        this();
        this.map = game.getMap();
        map.setCurrentTime(game.getCurrentTime());
        map.setUnitsAutoIncrementId(Unit.getAutoIncrementId());
        //gson.registerTypeAdapter();
    }

    public GameLoader(File file) {
        this();
        try {
            Scanner fileScanner = new Scanner(new FileInputStream(file));
            String json = fileScanner.nextLine();
            map = gson.create().fromJson(json, Map.class);
            //System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameLoader(String json) {
        this(null, json);
    }

    private GameLoader(ClientProtocol protocol, String json) {
        this(protocol);
        map = gson.create().fromJson(json, Map.class);
        Game.setCurrentTime(map.getCurrentTime());
        Unit.setAutoIncrementId(map.getUnitsAutoIncrementId());
    }

    // TODO: don't send hide things (powerups and doors)
    public static String getJson(Game currentGame) {
        return new GameLoader(currentGame).toJson();
    }

    public static Map getMap(ClientProtocol protocol, String json) {
        return new GameLoader(protocol, json).getMap();
    }

    public String toJson() {
        return gson.create().toJson(map);
    }

    public void save(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(toJson());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getMap() {
        return map;
    }

}
