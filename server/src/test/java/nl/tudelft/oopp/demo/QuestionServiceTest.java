package nl.tudelft.oopp.demo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.QuestionService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
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
public class QuestionServiceTest {

    @SpyBean
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RoomRepository roomRepository;


    private Room roomOne;

    /** Constructor for this test class.
     * Creates an example room in which test questions are asked.
     */
    public QuestionServiceTest() {
        this.roomOne = new Room(
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Software Quality And Testing", true);
    }



    @Test
    @Order(1)
    public void testClientDataParsing() {

        String payload = "2, When is lab assignment 3 due?, Sandra";

        String[] dataArray = payload.split(", ");
        long roomId = Long.valueOf(dataArray[0]);
        String questionText = dataArray[1];
        String questionOwner = dataArray[2];

        assertEquals(2, roomId);
        assertEquals("When is lab assignment 3 due?", questionText);
        assertEquals("Sandra", questionOwner);

    }


    @Test
    @Order(2)
    public void testGetRequest() {
        List<Question> questions = questionService.getQuestions();
        System.out.println(questions);
        assertEquals(new ArrayList<>(), questions);
    }

    @Test
    @Order(3)
    public void testInvalidDeleteRequest() {
        assertThrows(IllegalStateException.class, () -> {
            questionService.deleteQuestion((long)0);
        });
    }

    @Test
    @Order(4)
    public void testInvalidPutEditRequest() {
        assertThrows(IllegalStateException.class, () -> {
            questionService.updateQuestion((long)0, "Invalid update");
        });
    }


    @Test
    @Order(5)
    public void testInvalidPostRequest() {
        String payload = "0, When is lab assignment 3 due?, Sandra";
        assertThrows(IllegalStateException.class, () -> {
            questionService.addNewQuestion(payload);
        });
    }


    @Test
    @Order(6)
    public void testPostQuestionRequest() {

        roomRepository.saveAndFlush(roomOne);    // roomId 1
        List<Room> rooms = roomRepository.findAll();
        System.out.println(rooms);
        assertTrue(roomRepository.existsById((long)1));
        Room output = roomRepository.findById(1);
        assertEquals(roomOne, output);

        String payload = "1, When is lab assignment 3 due?, Sandra";
        Long questionId = questionService.addNewQuestion(payload);
        assertEquals(1, questionId);
        System.out.println("######### " + questionId + " ###########");   // questionId 1

        List<Question> questions = questionRepository.findAll();
        System.out.println("*********** " + questions + " *********");
        assertEquals("Sandra", questions.get(0).getOwner());

        System.out.println("%%%%%%%%%%%%%%%" + roomOne.getQuestions() + " %%%%%%%%%%%%%%%");
    }


    // Sequence generator continues from most recent count,
    // even though entries in repository are dropped each test.
    @Test
    @Order(7)
    public void testPostTwoQuestions() {

        roomRepository.saveAndFlush(roomOne);    // roomId 2

        //        List<Room> rooms = roomRepository.findAll();
        //        System.out.println("%%%%%%%%%% " + rooms.get(0).getRoomId() + " %%%%%%%%%%%%");

        String payload1 = "2, When is lab assignment 3 due?, Sandra";
        String payload2 = "2, Will answers be published?, Albert";

        Long questionId1 = questionService.addNewQuestion(payload1);   // questionId 2
        Long questionId2 = questionService.addNewQuestion(payload2);  // questionId 3
        assertEquals(2, questionId1);
        assertEquals(3, questionId2);
        System.out.println("######### " + questionId1 + " ###########");
        System.out.println("######### " + questionId2 + " ###########");

        List<Question> questions = questionRepository.findAll();
        System.out.println("*********** " + questions + " *********");
        assertEquals("Will answers be published?", questions.get(1).getText());
    }




    @Test
    @Order(8)
    public void testDeleteRequest() {

        roomRepository.saveAndFlush(roomOne);    // roomId 3

        String payload = "3, Could you repeat that?, Pim";
        Long questionId = questionService.addNewQuestion(payload);    // questionId 4
        assertEquals(4, questionId);
        System.out.println("######### " + questionId + " ###########");

        assertTrue(questionRepository.existsById((long)4));
        questionService.deleteQuestion((long)4);
        assertFalse(questionRepository.existsById((long)4));

    }



    @Test
    @Order(9)
    public void testPutRequest() {

        roomRepository.saveAndFlush(roomOne);  // roomId 4

        String payload = "4, Could you repeat that?, Pim";
        questionService.addNewQuestion(payload);   // questionId 5
        questionService.updateQuestion((long)5, "Can I update this?");

        List<Question> questions = questionRepository.findAll();
        System.out.println("*********** " + questions + " *********");
        assertEquals("Can I update this?", questions.get(0).getText());

    }


    @Test
    @Order(10)
    public void testAnswerNonExistingQuestion() {
        assertThrows(IllegalStateException.class, () -> {
            questionService.setAnswer((long)0, "Id 0 does not exist");
        });
    }


    @Test
    @Order(11)
    public void testEmptyAnswer() {

        roomRepository.saveAndFlush(roomOne);  // roomId 5

        String payload = "5, Can I get an empty answer?, Frank";
        questionService.addNewQuestion(payload);    // questionId 6

        List<Question> questions = questionRepository.findAll();
        System.out.println("*********** " + questions + " ********* ID: "
                + questions.get(0).getId());

        questionService.setAnswer((long)6, "");
        assertEquals("", questions.get(0).getAnswer());
    }

    @Test
    @Order(12)
    public void testAnswerPutRequest() {

        roomRepository.saveAndFlush(roomOne);  // roomId 6

        String payload = "6, Can I get an answer?, Jan";
        questionService.addNewQuestion(payload);  // questionId 7

        List<Question> questions = questionRepository.findAll();
        System.out.println("*********** " + questions + " *********ID: "
                + questions.get(0).getId());

        questionService.setAnswer((long)7, "Yes you can.");
        assertEquals("Yes you can.", questions.get(0).getAnswer());

    }


}
