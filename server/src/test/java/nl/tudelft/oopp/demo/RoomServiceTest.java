package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.RoomService;
import nl.tudelft.oopp.demo.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)   // automatically closes repo after each test
public class RoomServiceTest {

    // We are mocking the repositories, since these are tested in isolation.
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomService roomService;

    @Mock
    private Room roomOne;
    @Mock
    private Room roomTwo;
    @Mock
    private Room roomThree;


    @BeforeEach
    void setUp() {
        roomService = new RoomService(roomRepository);
    }

    /** Constructor for this test class.
     * Creates example rooms.
     */
    public RoomServiceTest() {
        roomOne = new Room(
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Software Quality And Testing", true);
        roomTwo = new Room(
                LocalDateTime.of(2021, Month.JANUARY, 1, 8, 45, 00),
                "Probability Theory And Statistics", true);
        roomThree = new Room(
                LocalDateTime.of(2021, Month.MARCH, 22, 14, 05, 00),
                "Computer Networks", false);
    }



    @Test
    public void getRoomsTest() {
        assertNotNull(roomService.getRooms());
        verify(roomRepository).findAll();
    }


    @Test
    public void getRoomByIdTest() {
        given(roomRepository.findById(1)).willReturn(roomOne);
        roomService.getRoomById(1);
        verify(roomRepository).findById(1);
    }

    @Test
    public void testAddRoom() {
        String payload = "Software Quality And Testing, 2021-05-19T10:45, true";

        roomService.addNewRoom(payload);

        ArgumentCaptor<Room> roomArgumentCaptor =
                ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(roomArgumentCaptor.capture());
        Room capturedRoom = roomArgumentCaptor.getValue();
        assertEquals("Software Quality And Testing", capturedRoom.getRoomName());
    }


    @Test
    public void testUpdateRoomSpeed() {
        given(roomRepository.findFirstByStudentsLink(anyString())).willReturn(roomOne);
        roomService.updateRoomSpeed("873jh783", "fast");
        verify(roomRepository).findFirstByStudentsLink(anyString());
        verify(roomOne, atLeastOnce()).votedTooFast();
        roomService.updateRoomSpeed("873jh783", "slow");
        verify(roomOne).votedTooSlow();
        roomService.updateRoomSpeed("873jh783", "reset");
        verify(roomOne).resetVote(anyString());
    }

    @Test
    public void testGetRoomByCode() {
        roomService.getRoomByCode("M7387hde");
        verify(roomRepository).findFirstByModeratorLink(anyString());
        roomService.getRoomByCode("37bjhbfe");
        verify(roomRepository).findFirstByStudentsLink(anyString());
    }

    @Test
    public void testUpdateRoomStatus() {
        given(roomRepository.findFirstByStudentsLink(anyString())).willReturn(roomOne);
        given(roomRepository.findFirstByModeratorLink(anyString())).willReturn(roomTwo);

        roomService.updateRoomStatusByLink("Mgdyueg34");
        verify(roomRepository).findFirstByModeratorLink(anyString());
        verify(roomTwo).end();
        roomService.updateRoomStatusByLink("434jdje6");
        verify(roomRepository).findFirstByStudentsLink(anyString());
        verify(roomOne).end();
    }





}
