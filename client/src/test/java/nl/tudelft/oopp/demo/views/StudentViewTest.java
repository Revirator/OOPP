package nl.tudelft.oopp.demo.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentViewTest {

    private StudentView studentView;
    private Question questionA;
    private Question questionB;

    /**
     * Constructor for this test, initializes two questions.
     */
    public StudentViewTest() {

        this.studentView = new StudentView();
        questionA = new Question(7, "Question A", "Patrick");
        questionB = new Question(7, "Question B", "Patricia");

        questionA.setId(0);
        questionB.setId(1);
    }

    @Test
    public void testAddExistingQ() {
        ObservableList<Question> questionsBefore = FXCollections
                .observableArrayList(List.of(questionA, questionB));

        studentView.setQuestions(questionsBefore);

        assertFalse(studentView.addQuestion(questionA));
    }

    @Test
    public void testAddQ() {
        ObservableList<Question> questionsBefore = FXCollections
                .observableArrayList(List.of(questionA));

        studentView.setQuestions(questionsBefore);

        assertTrue(studentView.addQuestion(questionB));

        ObservableList<Question> questionsAfter = FXCollections
                .observableArrayList(List.of(questionA, questionB));

        assertEquals(questionsAfter, studentView.getQuestions());
    }

}