package nl.tudelft.oopp.demo.entitytests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
import org.junit.jupiter.api.Test;



public class RoomTest {

    private Room roomOne;
    private Room roomTwo;
    private Room roomWithoutId;
    private Room emptyRoom;

    /**
     * Generating two rooms to be tested.
     */
    public RoomTest() {
        roomOne = new Room(1,
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Linear Algebra");
        roomTwo = new Room(5,
                LocalDateTime.of(2022, Month.OCTOBER, 22, 10, 30, 00),
                "CSE1200");
        roomWithoutId = new Room(LocalDateTime.of(2021, Month.JULY, 5, 13, 45, 00),
                "R&L", false);
        emptyRoom = new Room();
    }

    @Test
    public void testConstructor() {
        assertNotNull(roomOne);
        assertNotNull(roomTwo);
        assertNotNull(roomWithoutId);
        assertNotNull(emptyRoom);
    }



    @Test
    public void testGetRoomId() {
        assertEquals(0, roomWithoutId.getRoomId());
        assertEquals(5, roomTwo.getRoomId());
    }

    @Test
    public void testGetStartingTime() {
        assertEquals(LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                roomOne.getStartingTime());
    }

    @Test
    public void testGetRoomName() {
        assertEquals("Linear Algebra", roomOne.getRoomName());
        assertEquals("CSE1200", roomTwo.getRoomName());
    }


    @Test
    public void getIsActive() {
        assertTrue(roomOne.isActive());
        assertFalse(roomWithoutId.isActive());
    }


    @Test
    public void getQuestions() {
        assertNotNull(roomOne.getQuestions());
    }

    @Test
    public void testAddQuestion() {
        Question q = new Question(2, roomOne,
                "Is Java a programming language?","Albert",20);
        roomOne.addQuestion(q);
        assertEquals(List.of(q), roomOne.getQuestions());
    }

    @Test
    public void getParticipants() {
        assertNotNull(roomOne.getParticipants());
        assertNotNull(roomOne.getStudents());
        assertNotNull(roomOne.getModerators());
    }

    @Test
    public void testAddAndRemoveParticipant() {
        Student student1 = new Student(1, "Pieter", roomOne);
        roomOne.addParticipant(student1);
        assertEquals(List.of(student1), roomOne.getParticipants());
        roomOne.removeParticipant(student1);
        assertEquals(new ArrayList<>(), roomOne.getParticipants());
    }


    @Test
    public void setStudents() {
        Student student1 = new Student(1, "Pieter", roomOne);
        Student student2 = new Student(2, "Lisa", roomOne);
        roomTwo.setStudents(List.of(student1, student2));
        assertEquals(List.of(student1, student2), roomTwo.getStudents());
    }



    @Test
    public void testGetRoomLinks() {
        assertNotNull(roomOne.getStudentsLink());
        assertNotNull(roomOne.getModeratorLink());
        assertNotNull(roomTwo.getStudentsLink());
        assertNotNull(roomTwo.getModeratorLink());
    }


    @Test
    public void testLinkGenerator() {
        assertNotEquals(roomTwo.getStudentsLink(), roomOne.getStudentsLink());
        assertNotEquals(roomTwo.getModeratorLink(), roomOne.getModeratorLink());
    }

    @Test
    public void testGetLiveFeedback() {
        assertEquals(0, roomOne.getPeopleThinkingLectureIsTooFast());
        assertEquals(0, roomOne.getPeopleThinkingLectureIsTooSlow());
    }

    @Test
    public void testLiveFeedback() {
        roomWithoutId.votedTooFast();
        assertEquals(1, roomWithoutId.getPeopleThinkingLectureIsTooFast());
        roomWithoutId.votedTooSlow();
        roomWithoutId.votedTooSlow();
        assertEquals(2, roomWithoutId.getPeopleThinkingLectureIsTooSlow());
    }

    @Test
    public void testResetFeedback() {
        roomOne.votedTooSlow();
        roomOne.votedTooFast();
        assertEquals(1, roomOne.getPeopleThinkingLectureIsTooSlow());
        assertEquals(1, roomOne.getPeopleThinkingLectureIsTooFast());
        roomOne.resetVote("resetSlow");
        roomOne.resetVote("resetFast");
        assertEquals(0, roomOne.getPeopleThinkingLectureIsTooSlow());
        assertEquals(0, roomOne.getPeopleThinkingLectureIsTooFast());
    }

    @Test
    public void testEndLecture() {
        assertTrue(roomTwo.isActive());
        roomTwo.end();
        assertFalse(roomTwo.isActive());
    }

    @Test
    public void testEquals() {
        assertNotEquals(roomTwo, roomOne);
        assertEquals(roomOne, roomOne);
        assertNotEquals(roomWithoutId, null);
    }

    @Test
    public void testToString() {
        assertEquals("Linear Algebra\n(10:45)\n2021/05/19", roomOne.toString());
        assertEquals("CSE1200\n(10:30)\n2022/10/22", roomTwo.toString());
    }
}

