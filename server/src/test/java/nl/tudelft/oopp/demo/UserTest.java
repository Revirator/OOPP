package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
import org.junit.jupiter.api.Test;



public class UserTest {

    private Room roomOne;
    private Student student1;
    private Student student2;
    private Student studentWithoutId;
    private Moderator moderator1;
    private Moderator moderatorWithoutId;
    private Moderator emptyModerator;
    private Student emptyStudent;

    /** Initializes test Room and Users.
     *
     */
    public UserTest() {
        roomOne = new Room(1,
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Linear Algebra");
        student1 = new Student(1, "Pieter", roomOne);
        student2 = new Student(2, "Lisa", roomOne);
        studentWithoutId = new Student("Tim", roomOne, "127.0.0.1");
        moderator1 = new Moderator(1, "Alan", roomOne);
        moderatorWithoutId = new Moderator("Ruben", roomOne);
        emptyModerator = new Moderator();
        emptyStudent = new Student();
    }

    @Test
    public void testConstructor() {
        assertNotNull(student1);
        assertNotNull(student2);
        assertNotNull(studentWithoutId);
        assertNotNull(moderator1);
        assertNotNull(moderatorWithoutId);
        assertNotNull(emptyModerator);
        assertNotNull(emptyStudent);
    }

    @Test
    public void testGetId() {
        assertEquals(1, student1.getId());
        assertEquals(0, studentWithoutId.getId());
        assertEquals(1, moderator1.getId());
        assertEquals(0, moderatorWithoutId.getId());
    }

    @Test
    public void testGetNickname() {
        assertEquals("Pieter", student1.getNickname());
        assertEquals("Alan", moderator1.getNickname());
    }

    @Test
    public void testGetRoom() {
        assertEquals(1, student1.getRoom());
        assertEquals(1, studentWithoutId.getRoom());
        assertEquals(1, moderator1.getRoom());
        assertEquals(1, moderatorWithoutId.getRoom());
    }

    @Test
    public void testNotEquals() {
        assertFalse(student1.equals(moderator1));
        assertFalse(student1.equals(student2));
        assertFalse(student1.equals(null));
    }

    @Test
    public void testEquals() {
        assertTrue(studentWithoutId.equals(studentWithoutId));
        Student copyStudent = new Student("Tim", roomOne, "145.8.0.1");
        assertTrue(studentWithoutId.equals(copyStudent));
    }

    @Test
    public void testGetIpAddress() {
        assertEquals("127.0.0.1", studentWithoutId.getIpAddress());
    }

    @Test
    public void testGetBanned() {
        assertFalse(student1.isBanned());
        assertFalse(student2.isBanned());
        assertFalse(studentWithoutId.isBanned());
    }

    @Test
    public void testBanStudent() {
        student2.ban();
        assertTrue(student2.isBanned());
    }

    @Test
    public void testToString() {
        assertEquals("Student Lisa in room 1", student2.toString());
        assertEquals("Moderator Alan in room 1", moderator1.toString());
    }



}
