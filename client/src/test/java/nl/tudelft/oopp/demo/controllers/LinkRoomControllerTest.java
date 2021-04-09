package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import javafx.scene.input.Clipboard;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


public class LinkRoomControllerTest {
    @Test
    public void setDataTest() {

        LinkRoomController lrc;

        try (MockedStatic<Clipboard> theMock = Mockito.mockStatic(Clipboard.class)) {
            theMock.when(() -> {
                Clipboard.getSystemClipboard();
            }).thenReturn(null);

            lrc = new LinkRoomController();
        }

        Room room = new Room("Room", LocalDateTime.now(), true);

        lrc.setData(room);

        assertEquals(lrc.getRoom(), room);
    }
}
