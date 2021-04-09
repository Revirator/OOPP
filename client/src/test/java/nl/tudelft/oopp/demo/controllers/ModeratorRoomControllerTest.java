package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.ModeratorView;
import org.junit.jupiter.api.Test;


public class ModeratorRoomControllerTest {

    private Room room;
    private User user;
    private ModeratorView view;
    private ModeratorRoomController mrc;


    @Test
    public void setDataTest() {

        room = new Room("testRoom", LocalDateTime.now(), true);
        user = new User("testUser", room);
        view = new ModeratorView();
        mrc = new ModeratorRoomController();

        try {
            mrc.setData(user, room, view);
        } catch (Exception e) {
            // Empty catch block just to ignore
            // UI issues during testing.
        }

        assertEquals(mrc.getRoom(), room);
        assertEquals(mrc.getUser(), user);
        assertEquals(mrc.getAppView(), view);
        assertEquals(mrc.isZenModeActive(), false);
    }
}
