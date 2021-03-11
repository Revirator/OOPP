package nl.tudelft.oopp.demo.data;

import java.time.LocalTime;

public class Question {

    private long id;
    private long roomId;
    private String text;
    private String answer;
    private String owner;
    private String time;
    private Integer upvotes;
    private boolean voted;

    /** Constructor with votes for testing purposes.
     * @param id - PK of this question.
     * @param roomId - ID of room where this question is asked. (FK)
     * @param text - String containing question.
     * @param owner - nickname of person who asked this question.
     * @param upvotes - used to prioritize questions.
     */
    public Question(long id, long roomId, String text, String owner, int upvotes) {
        this.id = id;
        this.roomId = roomId;
        this.text = text;
        this.answer = "";
        this.owner = owner;
        this.time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        this.upvotes = upvotes;
        voted = false;
    }

    /**
     * Getter for the question ID.
     * @return question ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the room ID.
     * @return room ID
     */
    public long getRoomId() {
        return roomId;
    }

    /**
     * Getter for the question String.
     * @return String containing question
     */
    public String getText() {
        return text;
    }

    /**
     * Updates the question.
     * @param text new question
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter for the answer of the question.
     * @return String containing the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Changes the answer of the question.
     * @param answer new answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Getter for the owner of the question.
     * @return String containing the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Getter for the time of when the question was asked.
     * @return time of question
     */
    public String getTime() {
        return time;
    }

    /**
     * Getter for the amount of votes.
     * @return amount of upvotes
     */
    public int getUpvotes() {
        return upvotes;
    }

    /**
     * Increases the amount of votes by 1 and saves
     * that the user voted.
     */
    public void upvote() {
        upvotes++;
        voted = true;
    }

    /**
     * Decreases the amount of votes by 1 and saves
     * that the user has no longer voted.
     */
    public void deUpvote() {
        upvotes--;
        voted = false;
    }

    /**
     * Checks if this question equals an object.
     * @param o object to compare to
     * @return true if equal, false if not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question1 = (Question) o;
        return getId().equals(question1.getId())
                && getRoomId() == question1.getRoomId()
                && getText().equals(question1.getText())
                && getAnswer().equals(question1.getAnswer())
                && getOwner().equals(question1.getOwner())
                && getTime().equals(question1.getTime())
                && upvotes.equals(question1.upvotes);
    }

    /**
     * Creates a String representation of the question.
     * @return String containing info about the question
     */
    @Override
    public String toString() {
        return time + " -- " + text + (!answer.equals("") ? "- " + answer : "");
    }


    /**
     * Check if the user has voted on the question.
     * @return true if voted, false if not
     */
    public boolean voted() {
        return voted;
    }
}
