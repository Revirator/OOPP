package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.time.LocalDateTime;
import java.util.ArrayList;

import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;

public class UserCommunicationTest {

    private static ClientAndServer mockServer;
    private final Student student;
    private final Moderator mod;

    /**
     * Constructor for this test.
     */
    public UserCommunicationTest() {
        Room room = new Room(2, "testRoom", LocalDateTime.now(), true);
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
    public void testGetStudent() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                    .withMethod("GET")
                    .withPath("/users/14")
            )
            .respond(
                    response()
                    .withStatusCode(200)
                    .withBody("")
            );
        assertNull(ServerCommunication.getStudent((long) 14));
    }

    @Test
    public void testGetStudentStatus() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                            .withMethod("GET")
                            .withPath("/users/15")
            )
            .respond(
                    response()
                            .withStatusCode(400)
                            .withBody("")
            );
        assertNull(ServerCommunication.getStudent((long) 15));
    }

    @Test
    public void testGetStudentError() {
        mockServer.stop();
        assertNull(ServerCommunication.getStudent((long) 15));
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
    public void testGetStudentsError() {
        mockServer.stop();
        assertNull(ServerCommunication.getStudents(1));
    }

    @Test
    public void testGetModerators() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                    .withMethod("GET")
                    .withPath("/rooms/moderators/24")
            )
            .respond(
                    response()
                    .withStatusCode(200)
                    .withBody("")
            );
        assertNull(ServerCommunication.getModerators(24));
    }

    @Test
    public void testGetModeratorsStatus() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                            .withMethod("GET")
                            .withPath("/rooms/moderators/27")
            )
            .respond(
                    response()
                            .withStatusCode(400)
                            .withBody("")
            );
        assertEquals(new ArrayList<Moderator>(), ServerCommunication.getModerators(27));
    }

    @Test
    public void testGetModeratorsError() {
        mockServer.stop();
        assertNull(ServerCommunication.getModerators(1));
    }

    @Test
    public void testSendNullUser() {
        assertEquals(-1, ServerCommunication.sendUser(null, 13));
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
    public void testSendUserError() {
        mockServer.stop();
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

    @Test
    public void testRemoveUserError() {
        mockServer.stop();
        assertFalse(ServerCommunication.removeUser(1));
    }

    @Test
    public void testBanStudent() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                    .withMethod("PUT")
                    .withPath("/users/ban/13")
            )
            .respond(
                    response()
                    .withStatusCode(200)
            );
        ServerCommunication.banStudent(student);

        mockServer.verify(
            request()
            .withMethod("PUT")
            .withPath("/users/ban/13"),
            VerificationTimes.once()
        );
    }

    @Test
    public void testBanStudentStatus() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                            .withMethod("PUT")
                            .withPath("/users/ban/13")
            )
            .respond(
                    response()
                            .withStatusCode(400)
            );
        ServerCommunication.banStudent(student);

        mockServer.verify(
            request()
                    .withMethod("PUT")
                    .withPath("/users/ban/13"),
            VerificationTimes.once()
        );
    }

    @Test
    public void testCheckIfBanned() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                    .withMethod("GET")
                    .withPath("/users/isBanned/2/IpAddress")
            )
            .respond(
                    response()
                    .withStatusCode(200)
                    .withBody("false")
            );
        assertFalse(ServerCommunication.checkIfBanned(student));
    }

    @Test
    public void testCheckIfBannedStatus() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                            .withMethod("GET")
                            .withPath("/users/isBanned/2/IpAddress")
            )
            .respond(
                    response()
                            .withStatusCode(400)
            );
        assertTrue(ServerCommunication.checkIfBanned(student));
    }

    @Test
    public void testCheckIfBannedError() {
        mockServer.stop();
        assertTrue(ServerCommunication.checkIfBanned(student));
    }
}
