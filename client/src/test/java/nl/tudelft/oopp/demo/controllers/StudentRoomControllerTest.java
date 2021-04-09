package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.StudentView;
import org.junit.jupiter.api.Test;


public class StudentRoomControllerTest {

    private Room room;
    private User user;
    private StudentView view;
    private StudentRoomController src;


    @Test
    public void testSetData() {

        room = new Room("testRoom", LocalDateTime.now(), true);
        user = new User("testUser", room);
        view = new StudentView();
        src = new StudentRoomController();

        try {
            this.src.setData(user, room, view);
        } catch (Exception e) {
            // Empty catch block just to ignore
            // UI issues during testing.
        }

        // Tests only assigning room, user, view and questionAllowed.
        assertEquals(src.getRoom(), room);
        assertEquals(src.getUser(), user);
        assertEquals(src.getAppView(), view);
        assertEquals(src.getQuestionAllowed(), true);
    }
}
