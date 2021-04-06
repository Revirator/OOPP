package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.time.LocalDateTime;

import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;

public class RoomCommunicationTest {

    private static ClientAndServer mockServer;
    private Room roomA;
    private Room roomB;
    private LocalDateTime dateTime;

    public RoomCommunicationTest() {
        dateTime = LocalDateTime.now();
        roomA = new Room("room A", dateTime, true);
        roomB = new Room("room B", dateTime, true);
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
    public void testGetEmptyRoom() {
        assertNull(ServerCommunication.getRoom("", false));
    }

    @Test
    public void testGetRoomLogStatus() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                            .withMethod("GET")
                            .withPath("/rooms/testCode/log")
            )
            .respond(
                    response()
                    .withStatusCode(400)
                    .withBody("")
            );
        assertNull(ServerCommunication.getRoom("testCode", true));
    }

    @Test
    public void testGetRoomNoLog() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                            .withMethod("GET")
                            .withPath("/rooms/testCode")
            )
            .respond(
                    response()
                            .withStatusCode(200)
                            .withBody("")
            );
        assertNull(ServerCommunication.getRoom("testCode", false));
    }

    @Test
    public void testMakeEmptyRoom() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                    .withMethod("POST")
                    .withPath("/rooms")
                    .withBody("room A, " + dateTime + ", true")
            )
            .respond(
                    response()
                    .withStatusCode(200)
                    .withBody("")
            );
        assertNull(ServerCommunication.makeRoom(roomA));
    }

    @Test
    public void testMakeEmptyRoomStatus() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                            .withMethod("POST")
                            .withPath("/rooms")
                            .withBody("room A, " + dateTime + ", true")
            )
            .respond(
                    response()
                            .withStatusCode(400)
                            .withBody("")
            );
        assertNull(ServerCommunication.makeRoom(roomA));
    }

    /**
     * Cannot test the case where the statusCode != 200.
     * The Alert won't be able to pop up and fail the test.
     */
    @Test
    public void testSendFeedback() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                            .withMethod("PUT")
                            .withPath("/rooms/testUrl/testFeedback")
            )
            .respond(
                    response()
                    .withStatusCode(200)
            );
        ServerCommunication.sendFeedback("testUrl", "testFeedback");

        mockServer.verify(
                request()
                        .withMethod("PUT")
                        .withPath("/rooms/testUrl/testFeedback"),
                VerificationTimes.once()
        );
    }

    /**
     * Cannot test the case where the statusCode != 200.
     * The Alert won't be able to pop up and fail the test.
     */
    @Test
    public void testUpdateRoomStatus() {
        new MockServerClient("localhost", 8080)
            .when(
                    request()
                    .withMethod("PUT")
                    .withPath("/rooms/update/testCode")
            )
            .respond(
                    response()
                    .withStatusCode(200)
            );
        ServerCommunication.updateRoomStatus("testCode");

        mockServer.verify(
            request()
                    .withMethod("PUT")
                    .withPath("/rooms/update/testCode"),
            VerificationTimes.once()
        );
    }

}
