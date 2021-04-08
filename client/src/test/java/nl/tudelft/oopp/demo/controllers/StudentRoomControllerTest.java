package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.StudentView;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StudentRoomControllerTest {

    private Room room;
    private User user;
    private StudentView view;
    private StudentRoomController src;

    @BeforeEach
    public void init() {
        room = new Room("testRoom", LocalDateTime.now(), true);
        user = new User("testUser", room);
        view = new StudentView();
        src = new StudentRoomController();

        try {
            this.src.setData(user, room, view);
        } catch (Exception e) { }
    }

    @Test
    public void testSetData() {

        // It tests only assigning room, user, view and questionAllowed.
        assertEquals(src.getRoom(), room);
        assertEquals(src.getUser(), user);
        assertEquals(src.getAppView(), view);
        assertEquals(src.getQuestionAllowed(), true);
    }
}
