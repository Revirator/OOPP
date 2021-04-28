package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class StudentTest {

    private final Student student1;
    private final Room room;

    /**
     * Constructor for this test.
     */
    public StudentTest() {
        room = new Room("testRoom", LocalDateTime.now(), true);
        student1 = new Student((long) 25, "Student1", room, "ipAddress", false);
    }

    @Test
    public void testGetIpAddress() {
        assertEquals("ipAddress", student1.getIpAddress());
    }

    @Test
    public void testIsBanned() {
        assertFalse(student1.isBanned());
    }

    @Test
    public void testGetRole() {
        assertEquals("Student", student1.getRole());
    }

    @Test
    public void testToString() {
        String expected = "Student Student1 in lecture testRoom";
        assertEquals(expected, student1.toString());
    }

    @Test
    public void testSuperNotEquals() {
        Student testStudent = new Student((long) 67, "NotStudent1!!!", room, "ipAddress", false);
        assertNotEquals(student1, testStudent);
    }

    @Test
    public void testSame() {
        Student testStudent = new Student((long) 25, "Student1", room, "ipAddress", false);
        assertEquals(student1, testStudent);
    }

    @Test
    public void testHash() {
        assertEquals(student1.hashCode(), student1.hashCode());
    }
}