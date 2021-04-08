package nl.tudelft.oopp.demo.entitytests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.Test;

public class QuestionTest {

    private Question question1;
    private Question question2;
    private Question question3;
    private Question questionWithoutId;
    private Room roomOne;

    /** Initializes test Room and Questions.
     *
     */
    public QuestionTest() {
        roomOne = new Room(1,
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Linear Algebra");
        question1 = new Question(1, roomOne,
                "What's the square root of -1?", "Senne",2);
        question2 = new Question(2, roomOne,
                "Is Java a programming language?","Albert",20);
        question3 = new Question(3,roomOne,
                "What is the idea behind the TU Delft logo?", "Henkie", 50);
        questionWithoutId = new Question(roomOne, "Could you repeat that?", "Victor");
    }

    @Test
    public void testConstructor() {
        assertNotNull(question1);
        assertNotNull(question2);
        assertNotNull(question3);
        assertNotNull(questionWithoutId);
    }

    @Test
    public void testGetId() {
        assertEquals(1, question1.getId());
        assertEquals(2, question2.getId());
        assertEquals(3, question3.getId());
        assertEquals(0, questionWithoutId.getId());
    }

    @Test
    public void testGetOwner() {
        assertEquals("Senne", question1.getOwner());
        assertEquals("Albert", question2.getOwner());
        assertEquals("Henkie", question3.getOwner());
        assertEquals("Victor", questionWithoutId.getOwner());
    }

    @Test
    public void getRoomTest() {
        assertEquals(1, question1.getRoomId());
        assertEquals(1, question2.getRoomId());
        assertEquals(1, question3.getRoomId());
        assertEquals(1, questionWithoutId.getRoomId());
    }

    @Test
    public void testGetText() {
        assertEquals("What's the square root of -1?", question1.getText());
    }

    @Test
    public void testSetText() {
        question1.setText("Are real numbers complex too?");
        assertNotNull(question1.getText());
        assertEquals("Are real numbers complex too?", question1.getText());
    }

    @Test
    public void testGetAnswer() {
        assertEquals("", question2.getAnswer());
        assertEquals("", questionWithoutId.getAnswer());
    }

    @Test
    public void testSetAnswer() {
        question1.setAnswer("This is only defined in the complex number system.");
        assertEquals("This is only defined in the complex number system.",
                question1.getAnswer());
    }

    @Test
    public void testGetUpvotes() {
        assertEquals(50, question3.getUpvotes());
        assertEquals(0, questionWithoutId.getUpvotes());
    }

    @Test
    public void testUpvote() {
        question3.upvote();
        question3.upvote();
        question3.upvote();
        assertEquals(53, question3.getUpvotes());
        question3.deUpvote();
        assertEquals(52, question3.getUpvotes());
    }

    @Test
    public void testGetIsAnswered() {
        assertFalse(question1.getIsAnswered());
        assertFalse(question2.getIsAnswered());
        assertFalse(questionWithoutId.getIsAnswered());
    }

    @Test
    public void testSetIsAnswered() {
        questionWithoutId.setAsAnswered();
        assertTrue(questionWithoutId.getIsAnswered());
    }

    @Test
    public void testIsBeingAnswered() {
        assertEquals(question1.isBeingAnswered(), false);
    }

    @Test
    public void testSetIsBeingAnswered() {
        question1.setIsBeingAnswered();
        assertEquals(question1.isBeingAnswered(), true);
    }

    @Test
    public void testSetIsNotBeingAnswered() {
        question1.setIsBeingAnswered();
        question1.setIsNotBeingAnswered();
        assertEquals(question1.isBeingAnswered(), false);
    }

    @Test
    public void testNotEquals() {
        assertFalse(question1.equals(question2));
        Question sameQuestion = new Question(roomOne,
                "Could you repeat that?", "Victor");
        assertTrue(sameQuestion.equals(questionWithoutId));
    }

    @Test
    public void testSameObject() {
        assertTrue(question2.equals(question2));
        assertFalse(question2.equals(null));
    }

    @Test
    public void testToString() {
        Question question4 = new Question(roomOne,
                "When is the deadline?", "Stefan");
        String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        System.out.println(question4);
        assertEquals("[" + time + "]\nStefan: When is the deadline?\n", question4.toString());
    }
}
