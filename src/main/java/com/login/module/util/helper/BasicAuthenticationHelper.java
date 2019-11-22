package com.login.module.util.helper;


import com.login.module.util.enums.MessageStatus;

public class BasicAuthenticationHelper extends Base64Helper {

    public static String createBasicAuthToken(String name, String password) {

        String credentials = name + ":" + password;
        String base64encodedString = encoder(credentials);

        if (base64encodedString.equals(credentials)) {
            return MessageStatus.BASE_64_NOT_SUPPORTED.toString();
        } else {
            return "Basic " + base64encodedString;
        }

    }
}
