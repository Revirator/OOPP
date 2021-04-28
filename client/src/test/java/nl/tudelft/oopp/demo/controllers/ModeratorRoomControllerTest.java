package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.ModeratorView;
import org.junit.jupiter.api.Test;

public class ModeratorRoomControllerTest {

    @Test
    public void setDataTest() {
        Room room = new Room("testRoom", LocalDateTime.now(), true);
        User user = new User("testUser", room);
        ModeratorView view = new ModeratorView();
        ModeratorRoomController mrc = new ModeratorRoomController();

        try {
            mrc.setData(user, room, view);
        } catch (Exception e) {
            // Empty catch block just to ignore UI issues during testing.
        }

        assertEquals(mrc.getRoom(), room);
        assertEquals(mrc.getUser(), user);
        assertEquals(mrc.getAppView(), view);
        assertFalse(mrc.isZenModeActive());
    }
}
