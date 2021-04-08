package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static nl.tudelft.oopp.demo.controllers.SplashController.joinRoomSanitation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockConstruction;

class SplashControllerTest {

    @Test
    void joinRoomSanitationTestWorking() {

        boolean flag;

        try (MockedConstruction<Alert> mc = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("Pavel","somecode");
        }

        assertEquals(flag,true);
    }

    @Test
    void joinRoomSanitationTest1() {

        boolean flag;

        try (MockedConstruction<Alert> mc = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("","");
        }

        assertEquals(flag,false);
    }

    @Test
    void joinRoomSanitationTest2() {

        boolean flag;

        try (MockedConstruction<Alert> mc = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("Pav el","somecode");
        }

        assertEquals(flag,false);
    }

    @Test
    void joinRoomSanitationTest3() {

        boolean flag;

        try (MockedConstruction<Alert> mc = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("Pavel","somecode/");
        }

        assertEquals(flag,false);
    }

    @Test
    void joinRoomSanitationTest4() {

        boolean flag;

        try (MockedConstruction<Alert> mc = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("P","somecode");
        }

        assertEquals(flag,false);
    }
}