package nl.tudelft.oopp.demo;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RoomTest {


    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void saveAndRetrieveQuestionTest() throws MalformedURLException {

        Room expected = new Room(
                1,
                LocalDateTime.of(2021, Month.APRIL, 16, 8, 45, 00 ),
                "Linear Algebra");
        roomRepository.save(expected);

        Room output = roomRepository.getOne((long) 1);
        assertEquals(expected, output);

    }

}
