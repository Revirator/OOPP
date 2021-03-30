package nl.tudelft.oopp.demo.data;

import java.time.LocalTime;

public class Question {

    private long id;
    private long room;
    private String text;
    private String answer;
    private String owner;
    private String time;
    private Integer upvotes;
    private boolean voted;
    private boolean isOwner;

    /** Constructor with Room object (matches server-side Question entity).
     * @param room - Room where this question is asked. (FK)
     * @param text - String containing question.
     * @param owner - nickname of person who asked this question.
     * @param isOwner - true if this user is owner, false otherwise.
     */
    public Question(long room, String text, String owner, boolean isOwner) {
        this.room = room;
        this.text = text;
        this.answer = "";
        this.owner = owner;
        this.time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        this.upvotes = 0;
        this.voted = false;
        this.isOwner = isOwner;
    }

    /** Constructor with Room id, not object (testing purposes).
     //* @param roomId - ID of Room where this question is asked. (FK)
     * @param text - String containing question.
     * @param owner - nickname of person who asked this question.
     */
    public Question(long room, String text, String owner) {
        this.room = room;
        this.text = text;
        this.answer = "";
        this.owner = owner;
        this.time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        this.upvotes = 0;
        this.voted = false;
        this.isOwner = true;
    }

    /** Constructor with votes for testing purposes.
     * @param id - PK of this question.
     * @param text - String containing question.
     * @param owner - nickname of person who asked this question.
     * @param upvotes - used to prioritize questions.
     */
    public Question(long room, long id, String text, String owner, int upvotes, boolean isOwner) {
        this.id = id;
        this.room = room;
        this.text = text;
        this.answer = "";
        this.owner = owner;
        this.time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        this.upvotes = upvotes;
        this.voted = false;
        this.isOwner = isOwner;
    }

    /**
     * Getter for the question ID.
     * @return question ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the question ID.
     * Retrieved by server (database sequence generator)
     */
    public void setId(long id) {
        this.id = id;
    }

    public long getRoom() {
        return this.room;
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
     * Setter for the amount of votes.
     */
    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
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
    public boolean equals(Object o) {  // Needs to be redone if we ever use it
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question1 = (Question) o;
        return getId().equals(question1.getId())
                && getOwner().equals(question1.getOwner());
    }

    /**
     * Creates a String representation of the question.
     * @return String containing info about the question
     */
    @Override
    public String toString() {
        return "[" + time + "] " + owner + ": " +  text
                + (!answer.equals("") ? "\n- " + answer : "\n");
    }


    /**
     * Check if the user has voted on the question.
     * @return true if voted, false if not
     */
    public boolean voted() {
        return voted;
    }


    /**
     * Check if the user is owner of this question.
     * @return true if this question was posted by this user, false otherwise.
     */
    public boolean isOwner() {
        return isOwner;
    }
}
