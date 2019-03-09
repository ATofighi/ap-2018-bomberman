package com.atofighi.bomberman.util;

import java.util.Base64;

public class Base64Helper {
    public static String encode(byte[] bytes) {
        return Base64.getEncoder().withoutPadding().encodeToString(bytes);
    }

    public static byte[] decode(String encodedString) {
        return Base64.getDecoder().decode(encodedString);
    }
}
