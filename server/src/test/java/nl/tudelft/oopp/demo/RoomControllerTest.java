package nl.tudelft.oopp.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.tudelft.oopp.demo.controllers.QuestionController;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.QuestionService;
import nl.tudelft.oopp.demo.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class RoomControllerTest {


    private final String roomCode = "434frfg3";
    private final long roomId = 5L;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RoomService roomService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private Room roomOne;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new RoomController(roomService)).build();
    }


    /** Constructor for this test class.
     * Creates example rooms.
     */
    public RoomControllerTest() {
        roomOne = new Room(
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Software Quality And Testing", true);
    }


    @Test
    public void testGetAllRooms() throws Exception {
        mockMvc.perform(get("/rooms")).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(roomService).getRooms();
    }


    @Test
    public void testGetRoomByCodeLog() throws Exception {
        mockMvc.perform(get("/rooms/{roomCode}/log", roomCode)).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(roomService).getRoomByCode(roomCode);
    }


    @Test
    public void testGetRoomByCode() throws Exception {
        mockMvc.perform(get("/rooms/{roomCode}", roomCode)).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(roomService).getRoomByCode(roomCode);
    }

    @Test
    public void testUpdateRoomStatus() throws Exception {
        mockMvc.perform(put("/rooms/update/{roomCode}", roomCode)).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(roomService).updateRoomStatusByLink(roomCode);
    }


    @Test
    public void testAddNewRoom() throws Exception {
        String payload = "Software Quality And Testing, 2021-05-19T10:45, true";

        mockMvc.perform(post("/rooms").content(payload)).
                andExpect(status().isOk());
        verify(roomService).addNewRoom(payload);
    }


    @Test
    public void testUpdateFeedback() throws Exception {
        mockMvc.perform(put("/rooms/{roomCode}/{feedback}",
                roomCode, "fast"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(roomService).updateRoomSpeed(roomCode, "fast");
    }

    @Test
    public void testGetStudents() throws Exception {
        given(roomService.getRoomById(roomId)).willReturn(roomOne);
        mockMvc.perform(get("/rooms/students/{roomId}", String.valueOf(roomId))).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(roomService).getRoomById(roomId);
    }

    @Test
    public void testGetModerators() throws Exception {
        given(roomService.getRoomById(roomId)).willReturn(roomOne);
        mockMvc.perform(get("/rooms/moderators/{roomId}", String.valueOf(roomId))).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(roomService).getRoomById(roomId);
    }

}
