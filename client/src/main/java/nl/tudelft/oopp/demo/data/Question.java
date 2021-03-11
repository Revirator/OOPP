package nl.tudelft.oopp.demo.data;

import java.time.LocalTime;
import java.util.Objects;


public class Question {

    private long id;
    private long roomId;
    private String text;
    private String answer;
    private String owner;
    private String time;
    private Integer upvotes;

    /** Constructor with upvotes for testing purposes.
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
    }

    public Long getId() {
        return id;
    }

    public long getRoomId() {
        return roomId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOwner() {
        return owner;
    }

    public String getTime() {
        return time;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void upvote() {
        upvotes++;
    }

    public void deUpvote(){
        upvotes--;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoomId(),
                getText(), getAnswer(), getOwner(), getTime(), upvotes);
    }

    @Override
    public String toString() {
        return time + " -- " + text + (!answer.equals("") ? "- " + answer : "");
    }


}
