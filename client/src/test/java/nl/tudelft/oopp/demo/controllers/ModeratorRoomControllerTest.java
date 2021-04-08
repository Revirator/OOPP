package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.ModeratorView;
import nl.tudelft.oopp.demo.views.StudentView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;


public class ModeratorRoomControllerTest {

    private Room room;
    private User user;
    private ModeratorView view;
    private ModeratorRoomController mrc;

    @BeforeEach
    public void init() {
        room = new Room("testRoom", LocalDateTime.now(), true);
        user = new User("testUser", room);
        view = new ModeratorView();
        mrc = new ModeratorRoomController();

        try {
            this.mrc.setData(user, room, view);
        } catch (Exception e) { }
    }

    @Test
    public void setDataTest() {

        try {
            mrc.setData(user, room, view);
        } catch (Exception e) {}

        assertEquals(mrc.getRoom(), room);
        assertEquals(mrc.getUser(), user);
        assertEquals(mrc.getAppView(), view);
        assertEquals(mrc.isZenModeActive(), false);
    }

//    @Test
//    public void roomRefresherTest() {
//
//        Room room2 = new Room("testRoom2", LocalDateTime.now(), true);
//
//        try (MockedStatic<ServerCommunication> theMock = Mockito.mockStatic(ServerCommunication.class)) {
//            theMock.when(() -> {
//                ServerCommunication.getRoom(anyString(), false);
//            }).thenReturn(room2);
//
//            assertEquals(mrc.getAppView().getRoom(), room2);
//        }
//    }
}
