package nl.tudelft.oopp.demo.controllertests;

import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.tudelft.oopp.demo.controllers.UserController;
import nl.tudelft.oopp.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final long roomId = 5L;
    private final long studentId = 1L;
    private final String ipAddress = "127.0.0.1";

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new UserController(userService)).build();
    }

    @Test
    public void testGetStudentById() throws Exception {
        mockMvc.perform(get("/users/{studentId}", studentId))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).getStudentById(studentId);
    }

    @Test
    public void testAddStudent() throws Exception {
        String payload = "Pim, 127.0.0.1, 1";
        mockMvc.perform(post("/users/addUser/Student").content(payload))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).addStudent(payload);
    }

    @Test
    public void testAddModerator() throws Exception {
        String payload = "Alan, 127.0.0.1, 2";
        mockMvc.perform(post("/users/addUser/Moderator").content(payload))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).addModerator(payload);
    }

    @Test
    public void testBanStudent() throws Exception {
        mockMvc.perform(put("/users/ban/{studentId}", studentId))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).banStudent(studentId);
    }

    @Test
    public void testRemoveUser() throws Exception {
        mockMvc.perform(delete("/users/remove/{userId}", 2L))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).removeUser(2L);
    }

    @Test
    public void testCheckIfBanned() throws Exception {
        mockMvc.perform(get("/users/isBanned/{roomId}/{ipAddress}",
                roomId, ipAddress)).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(userService).checkIfBanned(anyLong(), anyString());
    }
}
