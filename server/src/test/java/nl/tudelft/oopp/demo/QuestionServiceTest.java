package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.QuestionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class) // automatically closes repo after each test
public class QuestionServiceTest {

    // We are mocking the repositories, since these are tested in isolation.
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private RoomRepository roomRepository;

    @Mock
    private QuestionService questionService;
    @Mock
    private Question question1;

    private Room roomOne;
    private Room roomTwo;
    private Question question2;
    private Question question3;


    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionRepository, roomRepository);
    }



    /** Constructor for this test class.
     * Creates example rooms in which test questions are asked.
     */
    public QuestionServiceTest() {
        this.roomOne = new Room(
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Software Quality And Testing", true);
        this.roomTwo = new Room(
                LocalDateTime.of(2021, Month.MAY, 19, 10, 58, 00),
                "OOPP", true);
        question1 = new Question(1, roomOne,
                "What's the square root of -1?", "Senne",2);
        question2 = new Question(2, roomOne,
                "Is Java a programming language?","Albert",20);
        question3 = new Question(3,roomOne,
                "What is the idea behind the TU Delft logo?", "Henkie", 50);
    }



    @Test
    public void testClientDataParsing() {

        String payload = "2& Sandra& When is lab assignment 3 due?";

        String[] dataArray = payload.split("& ");
        long roomId = Long.valueOf(dataArray[0]);
        String questionOwner = dataArray[1];
        String questionText = dataArray[2];

        assertEquals(2, roomId);
        assertEquals("When is lab assignment 3 due?", questionText);
        assertEquals("Sandra", questionOwner);

    }



    @Test
    public void canGetQuestions() {
        List<Question> questions = questionService.getQuestions();
        assertEquals(new ArrayList<>(), questions);
        verify(questionRepository).findAllByOrderByUpvotesDesc();
    }

    @Test
    public void canGetAnsweredQuestions() {
        List<Question> questions = questionService.getAnsweredQuestions(0);
        assertEquals(new ArrayList<>(), questions);
        verify(questionRepository)
                .findQuestionsByRoomRoomIdAndIsAnsweredOrderByTimeDesc(0, true);
    }


    @Test
    public void canGetQuestionsByRoom() {
        List<Question> questions = questionService.getQuestionsByRoom(0);
        verify(questionRepository).findAllByOrderByUpvotesDesc();
    }


    @Test
    public void testInvalidPostRequest() {
        String payload = "0& Sandra& When is lab assignment 3 due?";

        given(roomRepository.existsById(any())).willReturn(false);

        assertThrows(IllegalStateException.class, () -> {
            questionService.addNewQuestion(payload);
        });
        verify(questionRepository, never()).save(any());
    }



    @Test
    public void testPostQuestionRequest() {

        String payload = "1& Sandra& When is lab assignment 3 due?";
        given(roomRepository.existsById(any())).willReturn(true);

        questionService.addNewQuestion(payload);

        ArgumentCaptor<Question> questionArgumentCaptor =
                ArgumentCaptor.forClass(Question.class);

        verify(questionRepository).save(questionArgumentCaptor.capture());

        Question capturedQuestion = questionArgumentCaptor.getValue();
        assertEquals(capturedQuestion.getText(), "When is lab assignment 3 due?");
    }


    @Test
    public void testAnswerNonExistingQuestion() {
        assertThrows(IllegalStateException.class, () -> {
            questionService.setAnswer((long)0, "Id 0 does not exist");
        });

    }



    @Test
    public void testEmptyAnswer() {

        given(questionRepository.findById((long)1))
                .willReturn(java.util.Optional.ofNullable(question1));

        assertDoesNotThrow(() -> {
            questionService.setAnswer((long)1, "Answer to test question");
        });

        verify(question1).setAnswer(anyString());
    }


    @Test
    public void testInvalidDeleteRequest() {

        given(questionRepository.existsById((long)0)).willReturn(false);

        assertThrows(IllegalStateException.class, () -> {
            questionService.deleteQuestion((long)0);
        });
        verify(questionRepository, never()).deleteById(any());
    }


    @Test
    public void testDeleteRequest() {

        given(questionRepository.existsById((long)1)).willReturn(true);

        assertDoesNotThrow(() -> {
            questionService.deleteQuestion((long)1);
        });

        verify(questionRepository).deleteById((long)1);

    }



    @Test
    public void testInvalidPutEditRequest() {

        assertThrows(IllegalStateException.class, () -> {
            questionService.updateQuestion((long)0, "Invalid update");
        });
    }


    @Test
    public void testAnswerPutRequest() {

        given(questionRepository.findById((long)1))
                .willReturn(java.util.Optional.ofNullable(question1));

        assertDoesNotThrow(() -> {
            questionService.updateQuestion((long)1, "Updated question");
        });

        verify(question1).setText(anyString());

    }



    @Test
    public void testInvalidMarkAnswered() {

        assertThrows(IllegalStateException.class, () -> {
            questionService.markQuestionAsAnswered((long)0);
        });
    }


    @Test
    public void testMarkAnswered() {
        given(questionRepository.findById((long)1))
                .willReturn(java.util.Optional.ofNullable(question1));

        assertDoesNotThrow(() -> {
            questionService.markQuestionAsAnswered((long)1);
        });

        verify(question1).setAsAnswered();
    }


    @Test
    public void testInvalidUpvote() {
        assertThrows(IllegalStateException.class, () -> {
            questionService.upvote((long)0);
        });
    }


    @Test
    public void testInvalidDeUpvote() {
        assertThrows(IllegalStateException.class, () -> {
            questionService.deUpvote((long)0);
        });
    }



    @Test
    public void testUpvote() {
        given(questionRepository.findById((long)1)).willReturn(
                java.util.Optional.ofNullable(question1));

        assertDoesNotThrow(() -> {
            questionService.upvote((long)1);
        });

        verify(question1).upvote();
    }


    @Test
    public void testDeUpvote() {
        given(questionRepository.findById((long)1)).willReturn(
                java.util.Optional.ofNullable(question1));

        assertDoesNotThrow(() -> {
            questionService.deUpvote((long)1);
        });

        verify(question1).deUpvote();
    }


}
