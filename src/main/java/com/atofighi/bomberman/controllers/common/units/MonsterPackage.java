package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.models.units.Monster;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MonsterPackage {
    private Class<? extends Monster> monsterClass;
    private Class<? extends MonsterController> controllerClass;


    public MonsterPackage(Class<? extends Monster> monsterClass, Class<? extends MonsterController> controllerClass) {
        this.monsterClass = monsterClass;
        this.controllerClass = controllerClass;
    }

    public int getLevel() {
        return newMonster(0, 0).getLevel();
    }

    public Monster newMonster(int x, int y) {

        try {
            Constructor<? extends Monster> constructor = monsterClass.getDeclaredConstructor(int.class, int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(x, y);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            try {
                Constructor<? extends Monster> constructor = monsterClass
                        .getDeclaredConstructor(getClass(), int.class, int.class);
                constructor.setAccessible(true);
                return constructor.newInstance(this, x, y);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public MonsterController newController(Monster monster, MapController mapController) {
        Constructor constructor = null;
        try {
            constructor = controllerClass.getDeclaredConstructor(monsterClass, MapController.class);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            try {
                constructor = controllerClass.getDeclaredConstructor(Monster.class, MapController.class);
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e3) {
                try {
                    constructor = controllerClass.getDeclaredConstructor(getClass(), monsterClass, MapController.class);
                    constructor.setAccessible(true);
                } catch (NoSuchMethodException e4) {
                    try {
                        constructor = controllerClass.getDeclaredConstructor(getClass(), Monster.class, MapController.class);
                        constructor.setAccessible(true);
                    } catch (NoSuchMethodException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        if (constructor != null && monster.getClass() == monsterClass) {
            try {
                if(constructor.getParameterCount() == 2) {
                    return (MonsterController) constructor.newInstance(monster, mapController);
                } else {
                    return (MonsterController) constructor.newInstance(this, monster, mapController);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Class<? extends Monster> getMonsterClass() {
        return monsterClass;
    }

    public Class<? extends MonsterController> getControllerClass() {
        return controllerClass;
    }

    public String getType() {
        return newMonster(0, 0).getType();
    }
}
