package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Room;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

public class RoomTest {

    private Room roomOne;
    private Room roomTwo;

    public RoomTest() {
        try {
            roomOne = new Room(LocalDateTime.now(), "Linear Algebra");
            roomTwo = new Room(1, LocalDateTime.now(), "CSE1200");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull(roomOne);
        assertNotNull(roomTwo);
        assertEquals("Linear Algebra", roomOne.getRoomName());
        assertNotNull(roomOne.getStudentsLink());
        assertNotNull(roomOne.getModeratorLink());
        assertEquals(1, roomTwo.getRoomId());
        assertEquals("CSE1200", roomTwo.getRoomName());
        assertNotNull(roomTwo.getStudentsLink());
        assertNotNull(roomTwo.getModeratorLink());
    }

    @Test
    public void testLinkGenerator() {
        assertTrue(roomOne.getStudentsLink().toString().contains("http://localhost:8080/room/"));
        assertTrue(roomOne.getModeratorLink().toString().contains("http://localhost:8080/room/M"));
        assertTrue(roomTwo.getStudentsLink().toString().contains("http://localhost:8080/room/"));
        assertTrue(roomTwo.getModeratorLink().toString().contains("http://localhost:8080/room/M"));
        assertNotEquals(roomTwo.getStudentsLink(), roomOne.getStudentsLink());
        assertNotEquals(roomTwo.getModeratorLink(), roomOne.getModeratorLink());
    }

    @Test
    public void testToString() {
        String date = LocalDateTime.now().toString().substring(0,10).replace("-","/");
        String time = LocalDateTime.now().toString().substring(11,16);
        assertEquals("Linear Algebra\n(" +
                time + ")\n" + date, roomOne.toString());
    }
}
