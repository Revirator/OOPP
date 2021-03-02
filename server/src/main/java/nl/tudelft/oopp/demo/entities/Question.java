package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy =GenerationType.SEQUENCE,
            generator = "question_sequence"
    )
    private long id;
    private long roomId;
    private String question;
    private String answer;
    private String owner;
    private String time;
    private Integer upvotes;

    public Question() {

    }

    public Question(long id, long roomId, String question, String owner) {
        this.id = id;
        this.roomId = roomId;
        this.question = question;
        this.answer = "";
        this.owner = owner;
        this.time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        this.upvotes = 0;
    }

    // constructor with upvotes for testing purposes
    public Question(long id, String roomCode, String question, String owner, int upvotes) {
        this.id = id;
        this.roomId = roomId;
        this.question = question;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question1 = (Question) o;
        return getId() == question1.getId() && getRoomId() == question1.getRoomId() && getQuestion().equals(question1.getQuestion()) && getAnswer().equals(question1.getAnswer()) && getOwner().equals(question1.getOwner()) && getTime().equals(question1.getTime()) && upvotes.equals(question1.upvotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoomId(), getQuestion(), getAnswer(), getOwner(), getTime(), upvotes);
    }

    @Override
    public String toString() {
        return time + " -- " + question + (!answer.equals("") ? "- " + answer : "");
    }
}
