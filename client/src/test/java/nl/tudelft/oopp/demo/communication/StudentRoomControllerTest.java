package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.controllers.StudentRoomController;
import nl.tudelft.oopp.demo.data.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentRoomControllerTest {


    private Question question1;
    private Question question2;
    private Question question3;


    /** Initializes test Questions.
     *
     */
    public StudentRoomControllerTest() {
        question1 = new Question(1,20,
                "What's the square root of -1?","Senne",20, true);
        question2 = new Question(2,20,
                "Is Java a programming language?","Albert",20, false);
        question3 = new Question(3,20,
                "What is the idea behind the TU Delft logo?", "Henkie", 50, false);
    }

    @Test
    public void testInvalidDeleteQuestion() {
        Assertions.assertFalse(StudentRoomController.deleteQuestion(null));
    }

    // Doesn't work in Gitlab (I somehow need to inject a repository?)

    //    @Test
    //    public void testInvalidEditQuestion() {
    //        Assertions.assertFalse(StudentRoomController.editQuestion(question1, ""));
    //        Assertions.assertFalse(StudentRoomController.editQuestion(null, "Test"));
    //    }
    //
    //    @Test
    //    public void testEditQuestionSingleWord() {
    //        Assertions.assertTrue(StudentRoomController.editQuestion(question2, "Update"));
    //        Assertions.assertEquals("Update", question2.getText());
    //    }

    //    @Test
    //    public void testEditQuestion() {
    //        Assertions.assertTrue(
    //        StudentRoomController.editQuestion(question3, "Can I change this?"));
    //        Assertions.assertEquals("Can I change this?", question3.getText());
    //    }

    // update when POST is done
    //    @Test
    //    public void deleteAndInsertQuestion() {
    //        Assertions.assertTrue(StudentRoomController.deleteQuestion(question3));
    //        Assertions.assertFalse(StudentRoomController.deleteQuestion(question3));
    //    }


}
