//package com.atofighi.bomberman;
//
//import com.atofighi.bomberman.addons.MonsterAddon;
//import com.atofighi.bomberman.controllers.common.units.MapController;
//import com.atofighi.bomberman.controllers.common.units.MonsterPackage;
//import com.atofighi.bomberman.models.Map;
//import com.atofighi.bomberman.models.units.Monster;
//import ir.atofighi.bomberman.BallMonster;
//
//import java.lang.reflect.Constructor;
//
//public class Test {
//    public static void main(String[] args) {
//        MonsterPackage mp = new MonsterAddon(new BallMonster());
//        Object monster = mp.newController(mp.newMonster(1, 1), new MapController(new Map("das", 1, 3, 3)));
//        System.out.println(monster);
//        Class clas = MonsterAddon.AddonMonsterController.class;
//        for (Constructor constructor : clas.getConstructors()) {
//            System.out.println(constructor);
//        }
//    }
//}