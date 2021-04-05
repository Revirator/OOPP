package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.data.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
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


    @Test
    public void testGetQuestions() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/questions/24")
                        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("[]")
                );

        assertEquals(new ArrayList<>(), ServerCommunication.getQuestions(24));
    }

    @Test
    public void testGetQuestionsStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/questions/25")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );

        assertEquals(new ArrayList<>(), ServerCommunication.getQuestions(25));
    }

    @Test
    public void testgetAnsQue() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("GET")
                        .withPath("/questions/answered/24")
                )
                .respond(
                        response()
                        .withStatusCode(200)
                        .withBody("[]")
                );
        assertEquals(new ArrayList<>(), ServerCommunication.getAnsweredQuestions(24));
    }

    @Test
    public void testgetAnsQueStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/questions/answered/25")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertEquals(new ArrayList<>(), ServerCommunication.getAnsweredQuestions(25));
    }

    @Test
    public void testDeleteQuestion() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("DELETE")
                        .withPath("/questions/30")
                )
                .respond(
                        response()
                        .withStatusCode(200)
                );
        assertTrue(ServerCommunication.deleteQuestion(30));
    }

    @Test
    public void testDeleteQuestionStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("DELETE")
                                .withPath("/questions/31")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertFalse(ServerCommunication.deleteQuestion(31));
    }

    @Test
    public void testSetEmptyA() {
        assertFalse(ServerCommunication.setAnswer(30, ""));
    }

    @Test
    public void testSetAnswer() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("PUT")
                        .withPath("/questions/setanswer/30")
                        .withBody("testAnswer")
                )
                .respond(
                        response()
                        .withStatusCode(200)
                );
        assertTrue(ServerCommunication.setAnswer(30, "testAnswer"));
    }

    @Test
    public void testSetAnswerStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/questions/setanswer/31")
                                .withBody("testAnswer")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertFalse(ServerCommunication.setAnswer(31, "testAnswer"));
    }

    @Test
    public void testEditQEmpty() {
        assertFalse(ServerCommunication.editQuestion(questionA.getId(), ""));
    }

    @Test
    public void testEditQuestion() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("PUT")
                        .withPath("/questions/30")
                        .withBody("updateBody")
                )
                .respond(
                        response()
                        .withStatusCode(200)
                );
        assertTrue(ServerCommunication.editQuestion(30, "updateBody"));
    }

    @Test
    public void testEditQuestionStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/questions/31")
                                .withBody("updateBody")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertFalse(ServerCommunication.editQuestion(31, "updateBody"));
    }

    @Test
    public void testPostNullQ() {
        assertEquals((long)-1, ServerCommunication.postQuestion(null));
    }

    @Test
    public void testPostQuestion() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("POST")
                        .withPath("/questions")
                        .withBody(questionA.getRoom() + "& " + questionA.getOwner()
                        + "& " + questionA.getText())
                )
                .respond(
                        response()
                        .withStatusCode(200)
                        .withBody("23")
                );
        assertEquals((long) 23, ServerCommunication.postQuestion(questionA));
    }

    @Test
    public void testPostQuestionStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/questions")
                                .withBody(questionB.getRoom() + "& " + questionB.getOwner()
                                        + "& " + questionB.getText())
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertEquals((long)-1, ServerCommunication.postQuestion(questionB));
    }

    @Test
    public void testMarkQAsAn() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("PUT")
                        .withPath("/questions/markAnswered/30")
                )
                .respond(
                        response()
                        .withStatusCode(200)
                );
        assertTrue(ServerCommunication.markQuestionAsAnswered(30));
    }

    @Test
    public void testMarkQAsStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/questions/markAnswered/31")
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertFalse(ServerCommunication.markQuestionAsAnswered(31));
    }

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

    @Test
    public void testUpvoteQuestionStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/questions/upvote/31"))
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertFalse(ServerCommunication.upvoteQuestion((long) 31));
    }

    @Test
    public void testDeUpvoteQ() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                        .withMethod("PUT")
                        .withPath("/questions/deupvote/30"))
                .respond(
                        response()
                        .withStatusCode(200)
                );
        assertTrue(ServerCommunication.deUpvoteQuestion((long) 30));
    }

    @Test
    public void testDeUpvoteQuestionStatus() {
        new MockServerClient("localhost", 8080)
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/questions/deupvote/31"))
                .respond(
                        response()
                                .withStatusCode(400)
                );
        assertFalse(ServerCommunication.deUpvoteQuestion((long) 31));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }
}