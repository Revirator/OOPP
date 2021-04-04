package nl.tudelft.oopp.demo.views;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.data.Question;
import org.junit.jupiter.api.Test;


public class ModeratorViewTest {

    private ModeratorView moderatorView;
    private Question questionA;
    private Question questionB;

    /**
     * Constructor for this test.
     */
    public ModeratorViewTest() {
        this.moderatorView = new ModeratorView();
        questionA = new Question(7, "Question A", "Patrick");
        questionB = new Question(7, "Question B", "Patricia");

        questionA.setId(0);
        questionB.setId(1);

        questionA.setUpvotes(20);
        questionB.setUpvotes(3);
    }

    @Test
    public void testUpdate() {
        ObservableList<Question> questionsBefore = FXCollections
                .observableArrayList(List.of(questionA, questionB));
        ObservableList<Question> answeredBefore = FXCollections
                .observableArrayList(new ArrayList<>());

        moderatorView.setQuestions(questionsBefore);
        moderatorView.setAnswered(answeredBefore);

        questionB.setUpvotes(60);

        moderatorView.update(List.of(questionA, questionB), new ArrayList<>());

        ObservableList<Question> questionsAfter = FXCollections
                .observableArrayList(List.of(questionB, questionA));

        assertEquals(questionsAfter, moderatorView.getQuestions());
    }

}