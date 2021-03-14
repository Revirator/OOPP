package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.oopp.demo.controllers.StudentRoomController;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;



public class StudentRoomControllerTest {

    private Room testRoom;


    /** Constructor.
     *  Initializes a test room in which all test questions are being posted.
     */
    public StudentRoomControllerTest() {
        testRoom = new Room(2, "Spring lecture", LocalDateTime.now(), true);
    }


    //    @Test
    //    public void testInvalidDeleteQuestion() {
    //        assertFalse(
    //                StudentRoomController.deleteQuestion(null));
    //    }
    //
    //    @Test
    //    public void testEditNonExistingQuestion() {
    //        assertFalse(StudentRoomController.editQuestion(
    //                null, "This should not work."));
    //    }

    @Test
    public void testPostAndEditEmptyQuestion() {

        Question question = new Question(testRoom, "Can I make this empty?", "Victor");
        Long outputId = ServerCommunication.postQuestion(question);
        assertNotNull(outputId);
        System.out.println("########### " + outputId + " ############");
        question.setId(outputId);

        assertFalse(StudentRoomController.editQuestion(
                question, ""));
    }


    @Test
    public void testPostAndEditQuestionSingleWord() {

        Question question = new Question(testRoom, "Can I change this into a word?", "Victor");
        Long outputId = ServerCommunication.postQuestion(question);
        assertNotNull(outputId);
        System.out.println("########### " + outputId + " ############");
        question.setId(outputId);

        assertTrue(StudentRoomController.editQuestion(question, "Update"));
        assertEquals("Update", question.getText());
        assertEquals("Victor", question.getOwner());
    }

    @Test
    public void testPostAndEditQuestion() {

        Question question = new Question(testRoom, "What is the square root of -1?", "Senne");
        Long outputId = ServerCommunication.postQuestion(question);
        assertNotNull(outputId);
        System.out.println("########### " + outputId + " ############");
        question.setId(outputId);

        assertEquals("What is the square root of -1?", question.getText());
        assertTrue(
            StudentRoomController.editQuestion(question, "Can I change this?"));
        assertEquals("Can I change this?", question.getText());
    }


    @Test
    public void postAndDeleteQuestion() {

        Question question = new Question(testRoom, "Can I delete this again?", "Victor");
        Long outputId = ServerCommunication.postQuestion(question);
        assertNotNull(outputId);
        System.out.println("########### " + outputId + " ############");
        question.setId(outputId);

        assertTrue(StudentRoomController.deleteQuestion(question));
    }


}
