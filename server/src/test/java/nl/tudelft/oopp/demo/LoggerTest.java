package nl.tudelft.oopp.demo;

import static nl.tudelft.oopp.demo.config.LoggerConfig.getFirstNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoggerTest {

    @Test
    public void getFirstNumberTest() {
        assertEquals(2, getFirstNumber("2& asda& 123"));
    }
}
