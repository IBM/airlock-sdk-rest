package com.ibm.app.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Strings {
    public static String removeRedundantApostrophes(String json) {
        json = json.trim();
        if (json.startsWith("\"")) {
            json = json.substring(1, json.length());
        }
        if (json.endsWith("\"")) {
            json = json.substring(0, json.length() - 1);
        }
        return json;
    }


    public static boolean compareStringMD5Signatures(String arg1, String arg2) {
        if (arg1 == null || arg2 == null){
            return false;
        }
        try {
            return ganerateMD5(arg1.trim()).equals(ganerateMD5(arg2.trim()));
        } catch (NoSuchAlgorithmException e) {
            return arg1.trim().equals(arg2.trim());
        }
    }


    private static String ganerateMD5(String string) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(string.getBytes(), 0, string.length());
        return new BigInteger(1, m.digest()).toString(16);
    }

}
