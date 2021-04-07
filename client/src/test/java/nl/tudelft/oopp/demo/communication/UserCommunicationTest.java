package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class UserCommunicationTest {

    private static ClientAndServer mockServer;
    private Room room;
    private Student student;
    private Moderator mod;

    public UserCommunicationTest() {
        room = new Room("testRoom", LocalDateTime.now(), true);
        student = new Student((long) 13, "testUser", room, "IpAddress", false);
        mod = new Moderator((long) 25, "testMod", room);
    }

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void testSendNullUser() {
        assertEquals(-1, ServerCommunication.sendUser(null, 13));
    }

    @Test
    public void testGetStudents() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("GET")
                        .withPath("/rooms/students/20")
                )
                .respond(
                        response()
                        .withStatusCode(200)
                        .withBody("")
                );
        assertNull(ServerCommunication.getStudents(20));
    }

    @Test
    public void testGetStudentsStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/rooms/students/21")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                                .withBody("")
                );
        assertEquals(new ArrayList<Student>(), ServerCommunication.getStudents(21));
    }

    @Test
    public void testSendStudent() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("POST")
                        .withPath("/users/addUser/Student")
                        .withBody("testUser, IpAddress, 20")
                )
                .respond(
                        response()
                        .withStatusCode(200)
                        .withBody("13")
                );
        assertEquals(13, ServerCommunication.sendUser(student, 20));
    }

    @Test
    public void testSendModStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/users/addUser/Moderator")
                                .withBody("testMod, 20")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                                .withBody("13")
                );
        assertEquals(-1, ServerCommunication.sendUser(mod, 20));
    }

    @Test
    public void testRemoveUser() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("DELETE")
                        .withPath("/users/remove/13")
                )
                .respond(
                        response()
                        .withStatusCode(200)
                );
        assertTrue(ServerCommunication.removeUser(13));
    }

    @Test
    public void testRemoveUserStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("DELETE")
                                .withPath("/users/remove/14")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertFalse(ServerCommunication.removeUser(14));
    }

}
