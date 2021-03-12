package nl.tudelft.oopp.demo.communication;

import java.time.LocalDateTime;

import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ServerCommunicationTest {


    @Test
    public void testDeleteNonExistingQuestion() {
        assertFalse(ServerCommunication.deleteQuestion(0));
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
