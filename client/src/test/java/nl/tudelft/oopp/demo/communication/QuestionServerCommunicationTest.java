package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;


public class QuestionServerCommunicationTest {

    private Room testRoom;

    public QuestionServerCommunicationTest() {
        this.testRoom = new Room(3, "Test room", LocalDateTime.now(), true);
    }

    @Test
    public void testDeleteNonExistingQuestion() {
        assertFalse(ServerCommunication.deleteQuestion(0));
    }


    @Test
    public void testEditNonExistingQuestion() {
        assertFalse(ServerCommunication.editQuestion(
                0, "This should not work."));
    }

    @Test
    public void testPostNullQuestion() {
        assertEquals(-1, ServerCommunication.postQuestion(null));
    }

    @Test
    public void testPostQuestion() {
        Question question = new Question(3, "Example question", "Henk");
        Long outputId = ServerCommunication.postQuestion(question);
        assertNotNull(outputId);
        System.out.println("########### " + outputId + " ############");
    }

    //    @Test
    //    public void testPostAndDeleteQuestion() {
    //        Question question = new Question(testRoom, "Example question to delete", "Bert");
    //        Long outputId = ServerCommunication.postQuestion(question);
    //        assertNotNull(outputId);
    //        System.out.println("########### " + outputId + " ############");
    //        assertTrue(ServerCommunication.deleteQuestion(outputId));
    //    }


    //    @Test
    //    public void testPostAndEditQuestion() {
    //        Question question = new Question(testRoom,
    //                "Example question to edit", "Joyce");
    //        Long outputId = ServerCommunication.postQuestion(question);
    //        assertNotNull(outputId);
    //        System.out.println("########### " + outputId + " ############");
    //        assertTrue(ServerCommunication.editQuestion(outputId, "Editing question."));
    //        assertEquals("Joyce", question.getOwner());
    //        // question ID and text are not modified by ServerCommunication.
    //        // this happens in StudentQuestionCell
    //    }

    @Test
    public void testEmptyEditQuestion() {
        Question question = new Question(testRoom.getRoomId(),
                "Example question to edit", "Joyce");
        Long outputId = ServerCommunication.postQuestion(question);
        assertNotNull(outputId);
        System.out.println("########### " + outputId + " ############");
        assertFalse(ServerCommunication.editQuestion(outputId, ""));
    }



}
