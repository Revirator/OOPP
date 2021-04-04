package nl.tudelft.oopp.demo.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student1;
    private Student student2;
    private Room room;

    /**
     * Constructor for this test.
     */
    public StudentTest() {
        room = new Room("testRoom", LocalDateTime.now(), true);
        student1 = new Student((long) 25, "Student1", room, "ipAddress", false);
        student2 = new Student("Student2", room);
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
    public void testNotInstance() {
        String word = "word";
        assertFalse(student1.equals(word));
    }

    @Test
    public void testSuperNotEquals() {
        Student testStudent = new Student((long) 67, "NotStudent1!!!", room, "ipAddress", false);
        assertFalse(student1.equals(testStudent));
    }

    @Test
    public void testSame() {
        Student testStudent = new Student((long) 25, "Student1", room, "ipAddress", false);
        assertTrue(student1.equals(testStudent));
    }

    @Test
    public void testHash() {
        assertEquals(student1.hashCode(), student1.hashCode());
    }
}