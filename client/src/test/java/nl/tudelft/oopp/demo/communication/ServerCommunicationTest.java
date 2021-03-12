package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;


public class ServerCommunicationTest {

    private Question question1;
    private Question question2;
    private Question question3;


    /** Initializes test Questions.
     *
     */
    public ServerCommunicationTest() {
        question1 = new Question(1,20,
                "What's the square root of -1?","Senne",20, true);
        question2 = new Question(2,20,
                "Is Java a programming language?","Albert",20, false);
        question3 = new Question(3,20,
                "What is the idea behind the TU Delft logo?", "Henkie", 50, false);
    }


    @Test
    public void testGetRoomEmptyString() {
        assertNull(ServerCommunication.getRoom(""));
    }

    @Test
    public void testMakeRoomEmptyString() {
        assertNull(ServerCommunication.makeRoom(null));
    }


}
