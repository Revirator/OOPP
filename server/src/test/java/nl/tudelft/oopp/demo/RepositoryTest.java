package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
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
    }



    @Test
    @Order(1)
    public void testSequenceGenerator() {
        roomRepository.saveAndFlush(calculus);
        roomRepository.saveAndFlush(wdty1);
        List<Room> rooms = roomRepository.findAll();
        System.out.println(rooms);
        assertEquals(1, rooms.get(0).getRoomId());
        assertEquals(2, rooms.get(1).getRoomId());

        assertTrue(roomRepository.existsById((long)1));
        assertTrue(roomRepository.existsById((long)2));
        assertFalse(roomRepository.existsById((long)3));

        System.out.println("################# RoomID 0 ############## = "
                + rooms.get(0).getRoomId());
        System.out.println("################# RoomID 1 ############## = "
                + rooms.get(1).getRoomId());
    }


    @Test
    @Order(2)
    public void saveAndRetrieveRoomTest() {
        roomRepository.saveAndFlush(calculus);
        Room output = roomRepository.getOne(calculus.getRoomId());
        assertEquals(calculus, output);
    }


    @Test
    @Order(3)
    public void saveAndRetrieveQuestionTest() {

        roomRepository.saveAndFlush(wdty1);
        Question expected = new Question(
                1, wdty1,
                "What is the basis of the zero subspace?",
                "Nadine", 55
        );
        questionRepository.save(expected);

        Question output = questionRepository.getOne((long) 1);
        assertEquals(expected, output);

    }

    @Test
    @Order(4)
    public void saveAndRetrieveStudentTest() {

        roomRepository.saveAndFlush(wdty1);
        Student expected = new Student(
                1, "Nadine", wdty1);
        studentUserRepository.save(expected);

        Student output = studentUserRepository.getOne((long) 1);
        assertEquals(expected, output);
    }

    @Test
    @Order(5)
    public void saveAndRetrieveModeratorTest() {

        roomRepository.saveAndFlush(wdty1);
        Moderator expected = new Moderator(
                2, "Christoph", wdty1);
        moderatorUserRepository.save(expected);

        Moderator output = moderatorUserRepository.getOne((long) 2);
        assertEquals(expected, output);
    }

}
