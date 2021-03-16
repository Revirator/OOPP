package nl.tudelft.oopp.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends User {


    public Student() {

    }

    public Student(String nickname, Room room) {
        super(nickname, room);
    }

    public Student(long id, String nickname, Room room) {
        super(id, nickname, room);
    }


    @Override
    public String toString() {
        return "Student " + super.getNickname() + " in room " + getRoom().getRoomId();
    }
}

