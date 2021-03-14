package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
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



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
public class QuestionServiceTest {

    @SpyBean
    private QuestionService questionService;

    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private RoomRepository roomRepository;
    //
    //    private Room roomOne;
    //    private Question question1;
    //    private Question question2;
    //
    //    public QuestionServiceTest() throws MalformedURLException {
    //
    //        roomOne = new Room(
    //                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
    //                "Software Quality And Testing", true);
    //        roomRepository.save(roomOne);

    //        question1 = new Question(roomOne,
    //                "What's the square root of -1?", "Senne");
    //        question2 = new Question(roomOne,
    //                "Is Java a programming language?","Albert");
    //        questionRepository.save(question1);
    //        questionRepository.save(question2);
    //    }


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
    public void testInvalidPutRequest() {
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

    //
    //    @Test
    //    @Order(6)
    //    public void testPostRequest() {
    //        System.out.println("########## " + question1.getId() + " ############");
    //    }



    //
    //    @Test
    //    public void testDeleteRequest() {
    //
    //    }
    //
    //    @Test
    //    public void testPutRequest() {
    //
    //    }
    //


}
