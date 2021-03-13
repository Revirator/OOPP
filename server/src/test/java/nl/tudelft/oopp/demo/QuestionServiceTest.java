package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class QuestionServiceTest {

//
//    private QuestionService questionService;
//    private QuestionRepository questionRepository;
//    private RoomRepository roomRepository;
//
//    @Autowired
//    public QuestionServiceTest(QuestionService questionService,
//                               QuestionRepository questionRepository,
//                               RoomRepository roomRepository) {
//        this.questionService = questionService;
//        this.questionRepository = questionRepository;
//        this.roomRepository = roomRepository;
//    }

    @Test
    public void testClientDataParsing() {

        String payload = "2, What is the meaning of life?, Sandra";

        String[] dataArray = payload.split(", ");
        long roomId = Long.valueOf(dataArray[0]);
        String questionText = dataArray[1];
        String questionOwner = dataArray[2];

        assertEquals(2, roomId);
        assertEquals("What is the meaning of life?", questionText);
        assertEquals("Sandra", questionOwner);

    }

//
//    @Test
//    public void testGetRequest() {
//
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
//    @Test
//    public void testPostRequest() {
//        assertTrue(roomRepository.existsById((long)12));
//    }


}
