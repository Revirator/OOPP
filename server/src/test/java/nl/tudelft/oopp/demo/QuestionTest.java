package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.Month;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestionTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void saveAndRetrieveQuestionTest() throws MalformedURLException {

        Room wdty1 = new Room(
                LocalDateTime.of(2021, Month.APRIL, 17, 12, 45, 00),
                "Web and Database");

        roomRepository.save(wdty1);

        Question expected = new Question(
                1, wdty1,
                "What is the basis of the zero subspace?",
                "Nadine", 55
        );
        questionRepository.save(expected);

        Question output = questionRepository.getOne((long) 1);
        assertEquals(expected, output);

    }


}
