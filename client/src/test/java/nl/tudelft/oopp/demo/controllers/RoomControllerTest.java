package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import org.junit.jupiter.api.Test;


public class RoomControllerTest {

    private Room testRoom;
    private ModeratorRoomController mrc;
    private User user;

    /**
     * Constructor for this test.
     * mrc could also be src, but we have to choose one here.
     */
    public RoomControllerTest() {
        testRoom = new Room("testRoom", LocalDateTime.now(), true);
        mrc = new ModeratorRoomController();
        user = new User("testUser", testRoom);
        mrc.setRoom(testRoom);
        mrc.setUser(user);
    }

    @Test
    public void testGetRoom() {
        assertEquals(testRoom, mrc.getRoom());
    }


    @Test
    public void testGetUser() {
        assertEquals(user, mrc.getUser());
    }

    @Test
    public void testEditEmptyQuestion() {
        Question question = new Question(23, "testQuestion", "Jessica");
        assertFalse(mrc.editQuestion(question, ""));
        assertEquals("testQuestion", question.getText());
    }

}