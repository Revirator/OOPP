package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.demo.data.Question;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import java.util.List;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


public class QuestionCommunicationTest {

    private static ClientAndServer mockServer;
    private Question questionA;
    private Question questionB;

    /**
     * Constructor for this test.
     */
    public QuestionCommunicationTest() {
        questionA = new Question(23, 30, "Question A", "Jente", 10, true);
        questionB = new Question(23, "Question B", "Bob");
    }

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
    }

    // doesn't work, format of res body not correct
//    @Test
//    public void testGetQuestions() {
//        String questionAString = questionA.toString();
//        String questionBString = questionB.toString();
//
//        new MockServerClient("localhost", 8080)
//                .when(
//                        request()
//                                .withMethod("GET")
//                                .withPath("/questions/23"),
//                        exactly(1))
//                .respond(
//                        response()
//                                .withStatusCode(200)
//                                .withBody("{ 'questions': [ questionAString, questionBString ] }")
//                );
//
//        List<Question> questionList = ServerCommunication.getQuestions(23);
//        List<Question> expectedQuestions = List.of(questionA, questionB);
//        assertEquals(expectedQuestions, questionList);
//    }

    @Test
    public void testUpvoteQuestion() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/questions/upvote/30"))
                .respond(
                        response()
                                .withStatusCode(200)
                );
        assertTrue(ServerCommunication.upvoteQuestion((long) 30));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }
}