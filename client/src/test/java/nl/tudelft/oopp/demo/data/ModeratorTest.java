package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ModeratorTest {

    private final Moderator moderator1;
    private final Moderator moderator2;

    /**
     * Constructor for this test.
     */
    public ModeratorTest() {
        Room room = new Room("testRoom", LocalDateTime.now(), true);
        moderator1 = new Moderator((long) 25, "Mod1", room);
        moderator2 = new Moderator("Mod2", room);
    }

    @Test
    public void testFirstConstructor() {
        assertNotNull(moderator1);
    }

    @Test
    public void testSecondConstructor() {
        assertNotNull(moderator2);
    }

    @Test
    public void testGetRole() {
        assertEquals("Moderator", moderator1.getRole());
    }

    @Test
    public void testToString() {
        String expected = "Moderator Mod1 in room testRoom";
        assertEquals(expected, moderator1.toString());
    }

}