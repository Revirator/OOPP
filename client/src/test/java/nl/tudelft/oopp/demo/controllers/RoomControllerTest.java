package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.AppView;
import nl.tudelft.oopp.demo.views.StudentView;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
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

    @Test
    public void testDeleteQuestion() {
        Question q = new Question(23, "testQuestion", "Jessica");

        try (MockedStatic<ServerCommunication> theMock = Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> {
                ServerCommunication.deleteQuestion(q.getId());
            }).thenReturn(true);

            assertEquals(mrc.deleteQuestion(q), true);
        }
    }

    @Test
    public void testSetData() {
        User dummyUser = new User("Dummy", testRoom);
        Room dummyRoom = new Room("Roomy", LocalDateTime.now(), true);
        AppView dummyAppView = new StudentView();

        try {
            mrc.setData(dummyUser, dummyRoom, dummyAppView);
        } catch (Exception e) {}

        assertEquals(mrc.getUser(), dummyUser);
        assertEquals(mrc.getRoom(), dummyRoom);
        assertEquals(mrc.getAppView(), dummyAppView);
    }


}