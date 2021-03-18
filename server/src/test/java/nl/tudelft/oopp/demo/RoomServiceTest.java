package nl.tudelft.oopp.demo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.RoomService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
public class RoomServiceTest {

    @SpyBean
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;


    private Room roomOne;
    private Room roomTwo;
    private Room roomThree;

    /** Constructor for this test class.
     * Creates an example room in which test questions are asked.
     */
    public RoomServiceTest() {
        this.roomOne = new Room(
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Software Quality And Testing", true);
        this.roomTwo = new Room(
                LocalDateTime.of(2021, Month.JANUARY, 1, 8, 45, 00),
                "Probability Theory And Statistics", true);
        this.roomThree = new Room(
                LocalDateTime.of(2021, Month.MARCH, 22, 14, 05, 00),
                "Computer Networks", false);
    }


    @Test
    @Order(1)
    public void getRoomsTest() {

        roomRepository.saveAndFlush(roomOne);   // roomId 1

        List<Room> rooms = roomService.getRooms();
        System.out.println(rooms);
        assertNotNull(rooms);
        assertEquals(roomOne, rooms.get(0));
        assertEquals("Software Quality And Testing"
                , rooms.get(0).getRoomName());
    }

    @Test
    @Order(2)
    public void getRoomByIdTest() {

        roomRepository.saveAndFlush(roomOne);  // roomId 2
        roomRepository.saveAndFlush(roomTwo);  // roomId 3
        roomRepository.saveAndFlush(roomThree);  // roomId 4

        assertEquals(roomOne, roomService.getRoomById(2));
    }





}
