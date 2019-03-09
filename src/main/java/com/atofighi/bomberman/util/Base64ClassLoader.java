package com.atofighi.bomberman.util;

import java.io.IOException;
import java.net.MalformedURLException;

public class Base64ClassLoader extends ClassLoader {

    public Base64ClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class loadClass(String name, String encodedString) {

        byte[] classData = Base64Helper.decode(encodedString);

        return defineClass(name,
                classData, 0, classData.length);

    }

}