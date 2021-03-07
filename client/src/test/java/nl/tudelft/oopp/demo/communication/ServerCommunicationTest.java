package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ServerCommunicationTest {

    @Test
    public void testGetRoomEmptyString() {
        assertNull(ServerCommunication.getRoom(""));
    }

}
