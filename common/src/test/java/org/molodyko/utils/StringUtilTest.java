package org.molodyko.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest {
    @Test
    public void testStringNotEmpty() {
        String text = "dmdev";
        String rightAnswer = "Dmdev";
        String afterChange = StringUtil.capitalize(text);
        assertEquals(afterChange, rightAnswer);
    }

    @Test
    public void testStringEmpty() {
        String text = "";
        String rightAnswer = "";
        String afterChange = StringUtil.capitalize(text);
        assertEquals(afterChange, rightAnswer);
    }

}
