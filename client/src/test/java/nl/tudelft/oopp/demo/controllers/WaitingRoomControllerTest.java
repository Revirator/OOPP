package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import org.junit.jupiter.api.Test;

class WaitingRoomControllerTest {

    @Test
    public void testSetData() {
        WaitingRoomController wrc = new WaitingRoomController();
        Room room = new Room("Room", LocalDateTime.now(), true);
        User user = new User("Pete", room);
        wrc.setData(user, room);
        assertEquals(room, wrc.getRoom());
        assertEquals(user, wrc.getStudent());
    }

}