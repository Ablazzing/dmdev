package org.molodyko;

import org.molodyko.utils.StringUtil;

import java.util.Locale;

import static org.molodyko.utils.StringUtil.isNullOrEmpty;

public class App {
    public static String capitalizeFirstAndLastLetters(String text) {
        if (isNullOrEmpty(text)) {
            System.out.println("Text is null or empty");
            return text;
        }
        String capitalizeFirstLetter = StringUtil.capitalize(text);
        int len = capitalizeFirstLetter.length();
        String replacingChar = capitalizeFirstLetter.substring(len - 1, len).toUpperCase(Locale.ROOT);
        return new StringBuilder(capitalizeFirstLetter).deleteCharAt(len - 1).append(replacingChar).toString();
    }
}
