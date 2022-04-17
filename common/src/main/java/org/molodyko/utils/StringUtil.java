package org.molodyko.utils;

import java.util.Locale;

public class StringUtil {

    public static String capitalize(String text) {
        if (isNullOrEmpty(text)) {
            System.out.println("Text is null or empty");
            return text;
        }
        String firstSymbol = text.substring(0,1);
        return text.replaceFirst(firstSymbol, firstSymbol.toUpperCase(Locale.ROOT));
    }

    public static boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
