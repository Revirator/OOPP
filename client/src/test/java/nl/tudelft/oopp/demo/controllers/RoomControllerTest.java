package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;

import java.time.LocalDateTime;

import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.osgi.service.TestFx;


@RunWith(MockitoJUnitRunner.class)
public class RoomControllerTest extends ApplicationTest {

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


    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("moderatorRoom.fxml"));
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
    public void testDeleteQuestionFound() {
        Question q = new Question(23, "testQuestion", "Jessica");

        try (MockedStatic<ServerCommunication> theMock = Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> {
                ServerCommunication.deleteQuestion(q.getId());
            }).thenReturn(true);

            assertEquals(mrc.deleteQuestion(q), true);
        }
    }

    @Test
    public void testDeleteQuestionNotFound() {
        Question q = new Question(23, "testQuestion", "Jessica");

        try (MockedStatic<ServerCommunication> theMock = Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> {
                ServerCommunication.deleteQuestion(q.getId());
            }).thenReturn(false);

            try (MockedConstruction<Alert> mc = mockConstruction(Alert.class)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                assertEquals(mrc.deleteQuestion(q), false);
            }
        }
    }

    @Test
    public void testUpvoteQuestionVotedFound() {
        Question q = new Question(23, "testQuestion", "Jessica");
        q.upvote();
        try (MockedStatic<ServerCommunication> theMock = Mockito.mockStatic(ServerCommunication.class)) {
            theMock.when(() -> {
                ServerCommunication.deUpvoteQuestion(q.getId());
            }).thenReturn(true);

            assertEquals(mrc.upvoteQuestion(q), true);
            assertEquals(q.voted(), false);
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