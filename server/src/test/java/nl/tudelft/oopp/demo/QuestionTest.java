package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class QuestionTest {


    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void saveAndRetrieveQuestionTest() {

        Question expected = new Question(
                5,
                "ROOM2",
                "What is the basis of the zero subspace?",
                "Nadine"
        );
        questionRepository.save(expected);

        Question output = questionRepository.getOne((long) 5);
        assertEquals(output, expected);

    }


}
