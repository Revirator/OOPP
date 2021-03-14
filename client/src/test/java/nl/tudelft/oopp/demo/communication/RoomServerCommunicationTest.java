package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;


public class RoomServerCommunicationTest {


    @Test
    public void testGetNewRoom() {

        Room newRoom = new Room("Example room", LocalDateTime.now(), true);
        newRoom = ServerCommunication.makeRoom(newRoom);

        assertNotNull(newRoom);
        assertEquals("Example room", newRoom.getRoomName());
        assertTrue(newRoom.isActive());

        assertNotNull(newRoom.getModeratorLink());
        assertNotNull(newRoom.getStudentsLink());
        System.out.println("########## " + newRoom.getModeratorLink() + " ############");
        System.out.println("########## " + newRoom.getRoomId() + " ############");

    }


    @Test
    public void testGetRoomEmptyString() {
        assertNull(ServerCommunication.getRoom(""));
    }

    @Test
    public void testMakeRoomEmptyString() {
        assertNull(ServerCommunication.makeRoom(null));
    }


}
