package nl.tudelft.oopp.demo.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.data.Question;
import org.junit.jupiter.api.Test;

public class StudentViewTest {

    private final StudentView studentView;
    private final Question questionA;
    private final Question questionB;

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