package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.controllers.SplashController.joinRoomSanitation;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockConstruction;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

class SplashControllerTest {

    @Test
    void joinRoomSanitationTestWorking() {
        boolean flag;

        try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("Pavel","somecode");
        }

        assertTrue(flag);
    }

    @Test
    void joinRoomSanitationTest1() {
        boolean flag;

        try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("","");
        }

        assertFalse(flag);
    }

    @Test
    void joinRoomSanitationTest2() {
        boolean flag;

        try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("Pav el","somecode");
        }

        assertTrue(flag);
    }

    @Test
    void joinRoomSanitationTest3() {
        boolean flag;

        try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("Pavel","somecode/");
        }

        assertFalse(flag);
    }

    @Test
    void joinRoomSanitationTest4() {
        boolean flag;

        try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
            flag = joinRoomSanitation("P","somecode");
        }

        assertFalse(flag);
    }
}