package nl.tudelft.oopp.demo;

import org.junit.jupiter.api.Test;

import static nl.tudelft.oopp.demo.config.LoggerConfig.getFirstNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class LoggerTest {

    @Test
    public void getFirstNumberTest() {
        assertEquals(2, getFirstNumber("2, asda, 123")); //getFirstNumber("2, asd, 1231")
    }
}
