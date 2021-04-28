package nl.tudelft.oopp.demo.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

import org.junit.jupiter.api.Test;

public class AppViewTest {

    private final AppView appView;
    private final Room room;
    private final User user;
    private final Question questionA;
    private final Question questionB;
    private final Question questionC;
    private final Student studentA;
    private final Student studentB;
    private final Moderator modA;
    private final Moderator modB;

    /**
     * Constructor for abstract class AppView.
     * Here studentView is used but modView would be fine too.
     */
    public AppViewTest() {
        this.appView = new StudentView();
        questionA = new Question(7, "Question A", "Patrick");
        questionB = new Question(7, "Question B", "Patricia");
        questionC = new Question(7, "Question C", "Peter");
        questionA.setId(0);
        questionB.setId(1);
        questionC.setId(2);
        room = new Room("testRoom", LocalDateTime.now(), true);
        user = new User("testUser", room);
        studentA = new Student("StudentA", room);
        studentB = new Student("StudentB", room);
        modA = new Moderator("ModeratorA", room);
        modB = new Moderator("ModeratorB", room);
    }

    @Test
    public void testUpdateNull() {
        ObservableList<Question> currentQuestions = appView.getQuestions();
        ObservableList<Question> currentAnswered = appView.getAnswered();
        List<Question> questionList = new ArrayList<>();
        List<Question> answeredList = new ArrayList<>();
        appView.update(questionList,answeredList);
        assertEquals(currentQuestions, appView.getQuestions());
        assertEquals(currentAnswered, appView.getAnswered());
    }

    @Test
    public void testUpdateRemoveDelQ() {
        ObservableList<Question> questionsBefore = FXCollections
                .observableArrayList(List.of(questionA, questionB, questionC));
        appView.setQuestions(questionsBefore);
        ObservableList<Question> questionsAfter = FXCollections
                .observableArrayList(List.of(questionA));
        appView.update(List.of(questionA), new ArrayList<>());
        ObservableList<Question> answeredBefore = appView.getAnswered();
        assertEquals(questionsAfter, appView.getQuestions());
        assertEquals(answeredBefore, appView.getAnswered());
    }

    @Test
    public void testUpdateRemoveDelA() {
        ObservableList<Question> questionsBefore = FXCollections
                    .observableArrayList(List.of(questionC));
        ObservableList<Question> answeredBefore = FXCollections
                .observableArrayList(List.of(questionA, questionB));
        appView.setQuestions(questionsBefore);
        appView.setAnswered(answeredBefore);
        ObservableList<Question> questionsAfter = FXCollections
                .observableArrayList(List.of(questionC));
        ObservableList<Question> answeredAfter = FXCollections
                .observableArrayList(List.of(questionA));
        appView.update(List.of(questionA, questionC), List.of(questionA));
        assertEquals(questionsAfter, appView.getQuestions());
        assertEquals(answeredAfter, appView.getAnswered());
    }

    @Test
    public void testUpdateNewQ() {
        List<Question> questionList = List.of(questionA, questionB, questionC);
        List<Question> answeredList = List.of(questionA, questionB);
        ObservableList<Question> questionsBefore = FXCollections
                .observableArrayList(List.of(questionA));
        ObservableList<Question> answeredBefore = FXCollections
                .observableArrayList(List.of(questionB));
        appView.setQuestions(questionsBefore);
        appView.setAnswered(answeredBefore);
        ObservableList<Question> questionsAfter = FXCollections
                .observableArrayList(List.of(questionC));
        ObservableList<Question> answeredAfter = FXCollections
                .observableArrayList(List.of(questionB, questionA));
        appView.update(questionList, answeredList);
        assertEquals(questionsAfter, appView.getQuestions());
        assertEquals(answeredAfter, appView.getAnswered());
    }

    @Test
    public void testUpdatePart() {
        ObservableList<User> participantsBefore = FXCollections
                .observableArrayList(List.of(studentA, modA));
        appView.setParticipants(participantsBefore);
        List<Student> studentList = Arrays.asList(studentA, studentB);
        List<Moderator> modList = Arrays.asList(modA, modB);
        appView.updateParticipants(studentList, modList);
        ObservableList<User> participantsAfter = FXCollections
                .observableArrayList(List.of(modA, modB, studentA, studentB));
        assertEquals(participantsAfter, appView.getParticipants());
    }

    @Test
    public void testGetUser() {
        assertNull(appView.getUser());
    }

    @Test
    public void testGetRoom() {
        assertNull(appView.getRoom());
    }

    @Test
    public void testSetData() {
        appView.setData(user, room);
        assertEquals(user, appView.getUser());
        assertEquals(room, appView.getRoom());
    }
}