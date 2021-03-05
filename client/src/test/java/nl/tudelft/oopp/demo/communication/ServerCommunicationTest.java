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

    // I don't know yet how to do proper tests that use the server
//    @Test
//    public void testGetRoomNotNull() {
//        assertNotNull(ServerCommunication.getRoom("0S"));
//    }
}
