package org.vinogradov.mysupport;

import java.util.stream.Stream;

public class HelperMethods {

    public static String delSpace(String str) {
        String newStr = str.trim();
        newStr = newStr.replaceAll(" ", "");
        return newStr;
    }
}
