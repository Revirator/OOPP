package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockConstruction;

import com.sun.tools.javac.Main;
import java.time.LocalDateTime;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.AppView;
import nl.tudelft.oopp.demo.views.StudentView;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

@RunWith(MockitoJUnitRunner.class)
public class RoomControllerTest extends ApplicationTest {

    private final Room testRoom;
    private final ModeratorRoomController mrc;
    private final User user;

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

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(Main.class
                .getResource("moderatorRoom.fxml")));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
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
    public void testEditFoundQuestion() {
        Question q = new Question(23, "testQuestion", "Jessica");
        String update = "New text";

        try (MockedStatic<ServerCommunication> theMock =
                     Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> ServerCommunication
                    .editQuestion(q.getId(), update)).thenReturn(true);

            assertTrue(mrc.editQuestion(q, update));
            assertEquals(q.getText(), update);
        }
    }

    @Test
    public void testEditNotFoundQuestion() {
        Question q = new Question(23, "testQuestion", "Jessica");
        String update = "New text";

        try (MockedStatic<ServerCommunication> theMock =
                     Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> ServerCommunication
                    .editQuestion(q.getId(), update)).thenReturn(false);

            try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
                assertFalse(mrc.editQuestion(q, update));
                assertEquals(q.getText(), "testQuestion");
            }
        }
    }

    @Test
    public void testDeleteQuestionFound() {
        Question q = new Question(23, "testQuestion", "Jessica");

        try (MockedStatic<ServerCommunication> theMock =
                     Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> ServerCommunication.deleteQuestion(q.getId())).thenReturn(true);

            assertTrue(mrc.deleteQuestion(q));
        }
    }

    @Test
    public void testDeleteQuestionNotFound() {
        Question q = new Question(23, "testQuestion", "Jessica");

        try (MockedStatic<ServerCommunication> theMock =
                     Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> ServerCommunication.deleteQuestion(q.getId())).thenReturn(false);

            try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
                assertFalse(mrc.deleteQuestion(q));
            }
        }
    }

    @Test
    public void testUpvoteFoundQuestionVoted() {
        Question q = new Question(23, "testQuestion", "Jessica");
        q.upvote();
        try (MockedStatic<ServerCommunication> theMock =
                     Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> ServerCommunication.deUpvoteQuestion(q.getId())).thenReturn(true);

            assertTrue(mrc.upvoteQuestion(q));
            assertFalse(q.voted());
        }
    }

    @Test
    public void testUpvoteNotFoundQuestionVoted() {
        Question q = new Question(23, "testQuestion", "Jessica");
        q.upvote();
        try (MockedStatic<ServerCommunication> theMock =
                     Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> ServerCommunication.deUpvoteQuestion(q.getId())).thenReturn(false);

            try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
                assertFalse(mrc.upvoteQuestion(q));
                assertTrue(q.voted());
            }
        }
    }

    @Test
    public void testUpvoteFoundQuestionNotVoted() {
        Question q = new Question(23, "testQuestion", "Jessica");
        q.deUpvote();
        try (MockedStatic<ServerCommunication> theMock =
                     Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> ServerCommunication.upvoteQuestion(q.getId())).thenReturn(true);

            assertTrue(mrc.upvoteQuestion(q));
            assertTrue(q.voted());
        }
    }

    @Test
    public void testUpvoteNotFoundQuestionNotVoted() {
        Question q = new Question(23, "testQuestion", "Jessica");
        q.deUpvote();
        try (MockedStatic<ServerCommunication> theMock =
                     Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> ServerCommunication.upvoteQuestion(q.getId())).thenReturn(false);

            try (MockedConstruction<Alert> ignored = mockConstruction(Alert.class)) {
                assertFalse(mrc.upvoteQuestion(q));
                assertFalse(q.voted());
            }
        }
    }

    @Test
    public void testSetData() {
        User dummyUser = new User("Dummy", testRoom);
        Room dummyRoom = new Room("Roomy", LocalDateTime.now(), true);
        AppView dummyAppView = new StudentView();

        try {
            mrc.setData(dummyUser, dummyRoom, dummyAppView);
        } catch (Exception e) {
            // Empty catch block just to ignore UI issues during testing.
        }

        assertEquals(mrc.getUser(), dummyUser);
        assertEquals(mrc.getRoom(), dummyRoom);
        assertEquals(mrc.getAppView(), dummyAppView);
    }
}