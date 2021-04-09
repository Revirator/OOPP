package nl.tudelft.oopp.demo.repositorytests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;

import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// DON'T RUN THE TESTS SEPARATELY!
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository<Student> studentUserRepository;
    @Autowired
    private UserRepository<Moderator> moderatorUserRepository;

    private Room calculus;
    private Room wdty1;
    private Question question1;
    private Question question2;
    private Question question3;

    /**
     * Generating two rooms to be tested.
     */
    public RepositoryTest() {
        calculus = new Room(
                LocalDateTime.of(2022, Month.OCTOBER, 22, 10, 30, 00),
                "CSE1200", true);
        wdty1 = new Room(
                LocalDateTime.of(2021, Month.APRIL, 17, 12, 45, 00),
                "Web and Database", true);
        question1 = new Question(
                1, wdty1,
                "What is the basis of the zero subspace?",
                "Nadine", 55);
        question2 = new Question(2, wdty1,
                "Is Java a programming language?","Albert",20);
        question3 = new Question(3, wdty1,
                "What is the idea behind the TU Delft logo?", "Henkie", 50);
    }

    @Test
    @Order(1)
    public void testRoomSequenceGenerator() {
        roomRepository.saveAndFlush(calculus);      // roomId 1
        roomRepository.saveAndFlush(wdty1);         // roomId 2
        List<Room> rooms = roomRepository.findAll();
        System.out.println(rooms);
        assertEquals(1, rooms.get(0).getRoomId());
        assertEquals(2, rooms.get(1).getRoomId());

        assertTrue(roomRepository.existsById((long)1));
        assertTrue(roomRepository.existsById((long)2));
        assertFalse(roomRepository.existsById((long)3));

        System.out.println("################# RoomID ############## = "
                + rooms.get(0).getRoomId());
        System.out.println("################# RoomID ############## = "
                + rooms.get(1).getRoomId());
    }

    @Test
    @Order(2)
    public void saveAndRetrieveRoomTest() {
        roomRepository.saveAndFlush(calculus);      // roomId 3
        Room output = roomRepository.getOne(calculus.getRoomId());
        assertEquals(calculus, output);
        System.out.println("TEST 2 ROOM ID: " + output.getRoomId());
        assertEquals(3, output.getRoomId());
    }

    @Test
    @Order(3)
    public void saveAndRetrieveQuestionTest() {
        roomRepository.saveAndFlush(wdty1);          // roomId 4
        questionRepository.save(question1);         // questionId 1

        Question output = questionRepository.getOne((long) 1);
        assertEquals(question1, output);
    }

    @Test
    @Order(4)
    public void testQuestionSequenceGenerator() {
        roomRepository.saveAndFlush(wdty1);              // roomId 5
        questionRepository.saveAndFlush(question2);     // questionId 2
        questionRepository.saveAndFlush(question3);     // questionId 3

        List<Question> questions = questionRepository.findAll();
        System.out.println("##########" + questions + "##########");

        assertEquals(2, questions.get(0).getId());
        assertEquals(3, questions.get(1).getId());
    }

    @Test
    @Order(5)
    public void testFindRoomById() {
        roomRepository.saveAndFlush(calculus);  // roomId 6
        Room output = roomRepository.findById(6);
        assertEquals(calculus, output);
    }

    @Test
    @Order(6)
    public void saveAndRetrieveStudentTest() {
        roomRepository.saveAndFlush(wdty1);     // roomId 7
        System.out.println("ROOMID TEST 6: " + wdty1.getRoomId());
        Student expected = new Student(
                1, "Nadine", wdty1);
        studentUserRepository.save(expected);

        Student output = studentUserRepository.getOne((long) 1);
        assertEquals(expected, output);

        List<Student> outputlist = studentUserRepository.findAllByRoomRoomId(7);
        System.out.println(outputlist);
        assertEquals(expected, outputlist.get(0));
        assertEquals(expected, studentUserRepository.findById(1));
        studentUserRepository.deleteById((long)1);
        assertNull(studentUserRepository.findById(1));
    }

    @Test
    @Order(7)
    public void saveAndRetrieveModeratorTest() {
        roomRepository.saveAndFlush(calculus);   // roomId 8
        System.out.println("ROOM ID TEST 7: " + calculus.getRoomId());

        Moderator expected = new Moderator(
                2, "Christoph", calculus);
        moderatorUserRepository.save(expected);

        Moderator output = moderatorUserRepository.getOne((long) 2);
        assertEquals(expected, output);

        List<Moderator> outputlist = moderatorUserRepository.findAllByRoomRoomId(8);
        System.out.println(outputlist);
        assertEquals(expected, outputlist.get(0));
        assertEquals(expected, moderatorUserRepository.findById(2));
    }

    @Test
    @Order(7)
    public void testFindQuestionsByUpvotes() {
        roomRepository.saveAndFlush(wdty1);              // roomId 9
        questionRepository.saveAndFlush(question1);     // questionId 4
        questionRepository.saveAndFlush(question2);     // questionId 5
        questionRepository.saveAndFlush(question3);     // questionId 6

        List<Question> questions = questionRepository.findAllByOrderByUpvotesDesc();
        System.out.println("##########" + questions + "##########");
        assertEquals(4, questions.get(0).getId());
        assertEquals(6, questions.get(1).getId());
        assertEquals(5, questions.get(2).getId());
    }

    @Test
    @Order(8)
    public void testGetAnsweredQuestionsByRoom() {
        assertEquals(new ArrayList<>(),
                questionRepository.findQuestionsByRoomRoomIdAndIsAnsweredOrderByTimeDesc(1, true));

        roomRepository.saveAndFlush(calculus);       // roomId 10
        Question answeredQ = new Question(calculus,
                "What is the idea behind the TU Delft logo?", "Henkie");
        answeredQ.setAsAnswered();
        questionRepository.saveAndFlush(answeredQ);  // questionId 7

        List<Question> answered =
                questionRepository.findQuestionsByRoomRoomIdAndIsAnsweredOrderByTimeDesc(10, true);
        assertEquals(7, answered.get(0).getId());
    }
}
