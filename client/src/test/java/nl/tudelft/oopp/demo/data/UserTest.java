package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class UserTest {

    private final User user1;
    private final User user2;
    private final Room room;

    /**
     * Constructor for this test.
     */
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
    public void testSame() {
        User testUser = new User((long) 23, "User1", room);
        assertEquals(user1, testUser);
    }

}