package com.login.module.util.helper;

import java.io.UnsupportedEncodingException;
import java.util.Base64;


public class Base64Helper {

    public static String encoder(String encodeString) {
        try {
            encodeString = Base64.getEncoder().encodeToString(encodeString.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    public static String decoder(String decodeString) {
        return new String(Base64.getDecoder().decode(decodeString));
    }
}
