package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class ServerCommunicationTest {


    @Test
    public void testDeleteNonExistingQuestion() {
        assertFalse(ServerCommunication.deleteQuestion(0));
    }


    @Test
    public void testPostNullQuestion() {
        assertNull(ServerCommunication.postQuestion(null));
    }

    @Test
    public void testPostQuestion() {
        Room testRoom = new Room(3, "Test room", LocalDateTime.now(), true);
        Question question = new Question(testRoom, "Example question", "Henk");
        assertNull(ServerCommunication.postQuestion(question));
        //        System.out.println("########### " + id + " ############");
    }

    @Test
    public void testGetNewRoom() {

        Room newRoom = new Room("Example room", LocalDateTime.now(), true);
        newRoom = ServerCommunication.makeRoom(newRoom);

        assertNotNull(newRoom);
        assertEquals("Example room", newRoom.getRoomName());
        assertTrue(newRoom.isActive());

        assertNotNull(newRoom.getModeratorLink());
        assertNotNull(newRoom.getStudentsLink());
        System.out.println("########## " + newRoom.getModeratorLink() + " ############" );
        System.out.println("########## " + newRoom.getRoomId() + " ############" );

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
