package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.Month;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionTest {


    private Question question1;
    private Question question2;
    private Question question3;
    private Room roomOne;


    /** Initializes test Questions.
     *
     */
    public QuestionTest() throws MalformedURLException {
        roomOne = new Room(1,
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Linear Algebra");
        question1 = new Question(1, roomOne,
                "What's the square root of -1?", "Senne",2);
        question2 = new Question(2, roomOne,
                "Is Java a programming language?","Albert",20);
        question3 = new Question(3,roomOne,
                "What is the idea behind the TU Delft logo?", "Henkie", 50);
    }


    @Test
    public void testConstructor() {
        assertNotNull(question1);
        assertNotNull(question2);
        assertNotNull(question3);
    }

    @Test
    public void testGetId() {
        assertEquals(1, question1.getId());
        assertEquals(2, question2.getId());
        assertEquals(3, question3.getId());
    }


    @Test
    public void testGetOwner() {
        assertEquals("Senne", question1.getOwner());
        assertEquals("Albert", question2.getOwner());
        assertEquals("Henkie", question3.getOwner());
    }

    @Test
    public void testGetText() {
        assertEquals("What's the square root of -1?", question1.getText());
    }

    @Test
    public void testGetUpvotes() {
        assertEquals(50, question3.getUpvotes());
    }

    @Test
    public void testSetAnswer() {
        question1.setAnswer("This is only defined in the complex number system.");
        assertEquals("This is only defined in the complex number system.",
                question1.getAnswer());
    }


}
