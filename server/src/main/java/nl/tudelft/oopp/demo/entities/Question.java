package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table (name = "questions")
public class Question {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "room")
    private String roomCode;
    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;
    @Column(name = "owner")
    private String owner;
    @Column(name = "time")
    private String time;
    @Column (name = "upvotes")
    private Integer upvotes;

    public Question() {

    }

    public Question(long id, String roomCode, String question, String owner) {
        this.id = id;
        this.roomCode = roomCode;
        this.question = question;
        this.answer = "";
        this.owner = owner;
        this.time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        this.upvotes = 0;
    }

    public long getId() {
        return id;
    }

    public String getRoomCode() {
        return roomCode;
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

    public void upvote() {
        upvotes++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question1 = (Question) o;
        return getId() == question1.getId() && getRoomCode().equals(question1.getRoomCode()) && getQuestion().equals(question1.getQuestion()) && getAnswer().equals(question1.getAnswer()) && getOwner().equals(question1.getOwner()) && getTime().equals(question1.getTime()) && upvotes.equals(question1.upvotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoomCode(), getQuestion(), getAnswer(), getOwner(), getTime(), upvotes);
    }

    @Override
    public String toString() {
        return question + (!answer.equals("") ? "- " + answer : "");
    }
}
