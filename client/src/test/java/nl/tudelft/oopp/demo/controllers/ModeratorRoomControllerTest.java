package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.controllers.ModeratorRoomController;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;

// Tests that require the server to run don't work in Gitlab!
public class ModeratorRoomControllerTest {


    private Room testRoom;
    private ModeratorRoomController mrc;


    /** Constructor.
     *  Initializes a test room in which all test questions are being posted.
     */
    public ModeratorRoomControllerTest() {
        testRoom = new Room(2, "Spring lecture", LocalDateTime.now(), true);
        this.mrc = new ModeratorRoomController();
    }



    @Test
    public void testPostAndEditEmptyQuestion() {

        Question question = new Question(3, "Can I make this empty?", "Victor");
        Long outputId = ServerCommunication.postQuestion(question);
        assertNotNull(outputId);
        System.out.println("########### " + outputId + " ############");
        question.setId(outputId);

        assertFalse(mrc.editQuestion(
                question, ""));
    }


    @Test
    public void testEmptyAnswer() {

        Question question = new Question(3, "Can I submit an empty answer?",
                "Victor");
        assertFalse(mrc.setAnswer(question,""));

    }



}
