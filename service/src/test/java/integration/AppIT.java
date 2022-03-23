package integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.molodyko.App;

public class AppIT {

    @Test
    public void testMainApp() {
        String text = "dmdev";
        String rightAnswer = "DmdeV";
        String result = App.capitalizeFirstAndLastLetters(text);
        Assertions.assertEquals(rightAnswer, result);
    }
}
