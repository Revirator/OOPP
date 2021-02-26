package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table (name = "questions")
public class Question {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "question")
    private String question;
    @Column(name = "owner")
    private String owner;
    @Column(name = "time")
    private LocalDateTime time;
    @Column (name = "upvotes")
    private Integer upvotes = 0;

    public Question() {

    }

    public Question(long id, String question, String owner, LocalDateTime time, Integer upvotes) {
        this.id = id;
        this.question = question;
        this.owner = owner;
        this.time = time;
        this.upvotes = upvotes;
    }

    public Question(long id, String question, String owner, LocalDateTime time) {
        this.id = id;
        this.question = question;
        this.owner = owner;
        this.time = time;
    }

    public Question(String question, String owner, LocalDateTime time) {
        this.question = question;
        this.owner = owner;
        this.time = time;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return id == question1.id && Objects.equals(question, question1.question) && Objects.equals(owner, question1.owner) && Objects.equals(time, question1.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, owner, time);
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", owner='" + owner + '\'' +
                ", time=" + time +
                ", upvotes=" + upvotes +
                '}';
    }
}
