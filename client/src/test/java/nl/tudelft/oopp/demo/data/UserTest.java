package nl.tudelft.oopp.demo.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user1;
    private User user2;
    private Room room;

    public UserTest() {
        room = new Room("testRoom", LocalDateTime.now(), true);
        user1 = new User((long) 23, "User1", room);
        user2 = new User("User2", null);
    }

    @Test
    public void testFirstConstructor() {
        assertNotNull(user1);
    }

    @Test
    public void testSecondConstructor() {
        assertNotNull(user2);
    }

    @Test
    public void testGetId() {
        assertEquals(23, user1.getId());
    }

    @Test
    public void testGetNickname() {
        assertEquals("User1", user1.getNickname());
    }

    @Test
    public void testGetRoom() {
        assertEquals(room, user1.getRoom());
    }

    @Test
    public void testSetRoom() {
        user2.setRoom(room);
        assertEquals(room, user2.getRoom());
    }

    @Test
    public void testGetRoleUser() {
        assertEquals("User", user1.getRole());
    }

    @Test
    public void testNotInstance() {
        String word = "word";
        assertFalse(user1.equals(word));
    }

    @Test
    public void testSame() {
        User testUser = new User((long) 23, "User1", room);
        assertTrue(user1.equals(testUser));
    }

}