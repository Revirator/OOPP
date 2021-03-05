package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class ServerCommunicationTest {

    @Test
    public void testGetRoomEmptyString() {
        assertNull(ServerCommunication.getRoom(""));
    }

    @Test
    public void testGetRoomNotNull() {
        assertNotNull(ServerCommunication.getRoom("0S"));
    }
}
