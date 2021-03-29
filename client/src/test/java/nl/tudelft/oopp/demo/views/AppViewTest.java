package nl.tudelft.oopp.demo.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.data.Question;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppViewTest {

    private AppView studentView;
    private Question questionA;
    private Question questionB;
    private Question questionC;

    public AppViewTest() {

        this.studentView = new StudentView();
        questionA = new Question(7, "Question A", "Patrick");
        questionB = new Question(7, "Question B", "Patricia");
        questionC = new Question(7, "Question C", "Peter");

        questionA.setId(0);
        questionB.setId(1);
        questionC.setId(2);
    }

    @Test
    public void testUpdateNull() {
        ObservableList<Question> currentQuestions = studentView.getQuestions();
        ObservableList<Question> currentAnswered = studentView.getAnswered();
        List<Question> questionList = new ArrayList<>();
        List<Question> answeredList = new ArrayList<>();
        studentView.update(questionList,answeredList);
        assertEquals(currentQuestions, studentView.getQuestions());
        assertEquals(currentAnswered, studentView.getAnswered());
    }

    @Test
    public void testUpdateRemoveDelQ() {

        ObservableList<Question> questionsBefore = FXCollections.observableArrayList(List.of(questionA, questionB, questionC));
        studentView.setQuestions(questionsBefore);
        ObservableList<Question> answeredBefore = studentView.getAnswered();

        ObservableList<Question> questionsAfter = FXCollections.observableArrayList(List.of(questionA));

        studentView.update(List.of(questionA), new ArrayList<Question>());

        assertEquals(questionsAfter, studentView.getQuestions());
        assertEquals(answeredBefore, studentView.getAnswered());
    }

    @Test
    public void testUpdateRemoveDelA() {

        ObservableList<Question> questionsBefore = FXCollections.observableArrayList(List.of(questionC));
        ObservableList<Question> answeredBefore = FXCollections.observableArrayList(List.of(questionA, questionB));

        studentView.setQuestions(questionsBefore);
        studentView.setAnswered(answeredBefore);

        ObservableList<Question> questionsAfter = FXCollections.observableArrayList(List.of(questionC));
        ObservableList<Question> answeredAfter = FXCollections.observableArrayList(List.of(questionA));

        studentView.update(List.of(questionA, questionC), List.of(questionA));

        assertEquals(questionsAfter, studentView.getQuestions());
        assertEquals(answeredAfter, studentView.getAnswered());
    }

    @Test
    public void testUpdateNewQ() {

        List<Question> questionList = List.of(questionA, questionB, questionC);
        List<Question> answeredList = List.of(questionA, questionA);

        ObservableList<Question> questionsBefore = FXCollections.observableArrayList(List.of(questionA));
        ObservableList<Question> answeredBefore = FXCollections.observableArrayList(List.of(questionB));

        studentView.setQuestions(questionsBefore);
        studentView.setAnswered(answeredBefore);

        ObservableList<Question> questionsAfter = FXCollections.observableArrayList(List.of(questionC));
        ObservableList<Question> answeredAfter = FXCollections.observableArrayList(List.of(questionB, questionA));

        studentView.update(questionList, answeredList);

        assertEquals(questionsAfter, studentView.getQuestions());
        assertEquals(answeredAfter, studentView.getAnswered());
    }

}