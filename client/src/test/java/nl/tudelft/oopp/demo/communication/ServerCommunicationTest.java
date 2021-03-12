package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;



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
