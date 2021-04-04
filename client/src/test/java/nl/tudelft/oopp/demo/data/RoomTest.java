package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;


public class RoomTest {

    private Room room1;
    private Room room2;
    private Room room3;
    private LocalDateTime date;

    /**
     * Constructor for this test.
     */
    public RoomTest() {
        String dateString = "2021-04-04 19:20";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        date = LocalDateTime.parse(dateString, formatter);

        room1 = new Room(24, "studentLink", "moderatorLink", date,
                "testRoom1", true, new ArrayList<>(), 43, 25);
        room2 = new Room("testRoom2", date, true);
        room3 = new Room(23, "testRoom3", date, true);
    }

    @Test
    public void testFirstConstructor() {
        assertNotNull(room1);
    }

    @Test
    public void testSecondConstructor() {
        assertNotNull(room2);
    }

    @Test
    public void testThirdConstructor() {
        assertNotNull(room3);
    }

    @Test
    public void testGetRoomName() {
        assertEquals("testRoom1", room1.getRoomName());
    }

    @Test
    public void testGetStudentsLink() {
        assertEquals("studentLink", room1.getStudentsLink());
    }

    @Test
    public void testGetModeratorLink() {
        assertEquals("moderatorLink", room1.getModeratorLink());
    }

    @Test
    public void testGetRoomId() {
        assertEquals(24, room1.getRoomId());
    }

    @Test
    public void testGetStartingTime() {
        assertEquals(date, room1.getStartingTime());
    }

    @Test
    public void testIsActive() {
        assertTrue(room1.isActive());
    }

    @Test
    public void testGetPeopleThinkingLectureIsTooFast() {
        assertEquals(25, room1.getPeopleThinkingLectureIsTooFast());
    }

    @Test
    public void testGetPeopleThinkingLectureIsTooSlow() {
        assertEquals(43, room1.getPeopleThinkingLectureIsTooSlow());
    }

    @Test
    public void testEnd() {
        room1.end();
        assertFalse(room1.isActive());
    }

    @Test
    public void testGetParticipants() {
        assertEquals(new ArrayList<User>(), room1.getParticipants());
    }

    @Test
    public void testGetStudents() {
        assertEquals(new ArrayList<User>(), room1.getStudents());
    }

    @Test
    public void testGetModerators() {
        assertEquals(new ArrayList<User>(), room1.getModerators());
    }

    @Test
    public void testNotInstance() {
        String word = "word";
        assertFalse(room1.equals(word));
    }

    @Test
    public void testSame() {
        Room testRoom = new Room(24, "studentLink", "moderatorLink", date,
                "testRoom1", true, new ArrayList<>(), 43, 25);
        assertTrue(room1.equals(testRoom));
    }

    @Test
    public void testHash() {
        assertEquals(room1.hashCode(), room1.hashCode());
    }
}