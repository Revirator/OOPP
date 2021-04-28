package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.StudentView;
import org.junit.jupiter.api.Test;

public class StudentRoomControllerTest {

    @Test
    public void testSetData() {
        Room room = new Room("testRoom", LocalDateTime.now(), true);
        User user = new User("testUser", room);
        StudentView view = new StudentView();
        StudentRoomController src = new StudentRoomController();
        try {
            src.setData(user, room, view);
        } catch (Exception e) {
            // Empty catch block just to ignore
            // UI issues during testing.
        }
        assertEquals(src.getRoom(), room);
        assertEquals(src.getUser(), user);
        assertEquals(src.getAppView(), view);
        assertTrue(src.getQuestionAllowed());
    }
}
