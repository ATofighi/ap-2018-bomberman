package com.atofighi.bomberman.controllers.common;

import com.atofighi.bomberman.addons.MonsterAddon;
import com.atofighi.bomberman.controllers.client.ClientProtocol;
import com.atofighi.bomberman.controllers.common.Game;
import com.atofighi.bomberman.controllers.common.units.MonsterPackage;
import com.atofighi.bomberman.models.units.Blacki;
import com.google.gson.*;
import com.atofighi.bomberman.models.units.Monster;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

class MonsterAdapter implements JsonDeserializer<Monster> {

    ClientProtocol protocol;

    public MonsterAdapter(ClientProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public Monster deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        String monsterType = jsonElement.getAsJsonObject().get("type").getAsString();
        for (MonsterPackage monsterPackage : Game.getAvailableMonsters()) {
            if (monsterPackage.getType().equals(monsterType)) {
                return getMonster(jsonElement, jsonDeserializationContext, monsterPackage);
            }
        }

        if (protocol != null) {
            protocol.addMonster(monsterType);
            for (MonsterPackage monsterPackage : Game.getAvailableMonsters()) {
                if (monsterPackage.getType().equals(monsterType)) {
                    return getMonster(jsonElement, jsonDeserializationContext, monsterPackage);
                }
            }
        }

        System.err.println("Unknown monster :( ==> " + monsterType);
        for (MonsterPackage monsterPackage : Game.getAvailableMonsters()) {
            System.err.println(monsterPackage.getType());
        }
        return jsonDeserializationContext.deserialize(jsonElement, Blacki.class);
    }

    private Monster getMonster(JsonElement jsonElement,
                               JsonDeserializationContext jsonDeserializationContext,
                               MonsterPackage monsterPackage) {
        Monster monster = jsonDeserializationContext
                .deserialize(jsonElement, monsterPackage.getMonsterClass());
        if (monsterPackage instanceof MonsterAddon) {
            try {
                Field this$0 = monsterPackage.getMonsterClass().getDeclaredField("this$0");
                this$0.setAccessible(true);
                this$0.set(monster, monsterPackage);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return monster;
    }
}
