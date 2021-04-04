package nl.tudelft.oopp.demo.data;

import java.time.LocalDateTime;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class QuestionTest {


    private Question question1;
    private Question question2;
    private Question question3;
    private Room activeRoom;
    private Room endedRoom;


    /** Initializes test Questions.
     *
     */
    public QuestionTest() {
        activeRoom = new Room(1, "Active room", LocalDateTime.now(), true);
        endedRoom = new Room(2, "Ended lecture", LocalDateTime.now(), false);
        question1 = new Question(3, "What is the meaning of life?", "Pavel");
        question2 = new Question(4, "When are the grades out?", "Emke");
        question3 = new Question(5, "Can I revisit the lecture?", "Bora");
    }


    @Test
    public void testBasicConstructor() {
        assertNotNull(question1);
        assertNotNull(question2);
        assertNotNull(question3);
    }

    @Test
    public void testSecondConstructor() {
        Question question = new Question(123, "New question", "Paul", true);
        assertNotNull(question);
    }

    @Test
    public void testThirdConstructor() {
        Question question = new Question(123, 2, "New question", "Paul", 23, true);
        assertNotNull(question);
    }

    @Test
    public void testGetId() {
        assertEquals(0, question1.getId());
        assertEquals(0, question2.getId());
        assertEquals(0, question3.getId());
    }

    @Test
    public void testSetId() {
        assertEquals(0, question1.getId());
        question1.setId(42);
        assertEquals(42, question1.getId());
    }


    @Test
    public void testGetOwner() {
        assertEquals("Pavel", question1.getOwner());
        assertEquals("Emke", question2.getOwner());
        assertEquals("Bora", question3.getOwner());
    }

    @Test
    public void testGetText() {
        assertEquals("What is the meaning of life?", question1.getText());
    }

    @Test
    public void testUpvoting() {
        assertEquals(0, question3.getUpvotes());
        question3.upvote();
        assertEquals(1, question3.getUpvotes());
        question3.deUpvote();
        assertEquals(0, question3.getUpvotes());
    }

    @Test
    public void testSetAnswer() {
        question2.setAnswer("Have some patience.");
        assertEquals("Have some patience.",
                question2.getAnswer());
    }

    @Test
    public void testNotInstance() {
        String word = "word";
        assertFalse(question1.equals(word));
    }

    @Test
    public void testSameQuestion() {
        Question questionA = new Question(123, 2, "New question", "Paul", 23, true);
        Question questionB = new Question(123, 2, "New question", "Paul", 23, true);
        assertTrue(questionA.equals(questionB));
    }

    @Test
    public void testToString() {
        String expected = "[" + question1.getTime() + "] "
                + question1.getOwner() + ": " + question1.getText() + "\n";
        assertEquals(expected, question1.toString());
    }

    @Test
    public void testVoted() {
        assertFalse(question1.voted());
    }

    @Test
    public void testIsOwner() {
        assertTrue(question1.isOwner());
    }
}
