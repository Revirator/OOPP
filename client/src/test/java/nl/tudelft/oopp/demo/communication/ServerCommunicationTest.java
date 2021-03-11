package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;


public class ServerCommunicationTest {

    @Test
    public void testGetRoomEmptyString() {
        assertNull(ServerCommunication.getRoom(""));
    }

    @Test
    public void testMakeRoomEmptyString() {
        assertNull(ServerCommunication.makeRoom(null));
    }

    /**
     * Doesn't work in pipeline Gitlab, but does work here.
     * Works when you start "Demoapplication" first.
    @Test
    public void testMakeRoom() {
        Room room = new Room("CSE1105", LocalDateTime.now(), true);
        Room updatedRoom = ServerCommunication.makeRoom(room);
        assertNotNull(updatedRoom.getModeratorLink());
        assertNotNull(updatedRoom.getStudentsLink());
    }
    */

}
