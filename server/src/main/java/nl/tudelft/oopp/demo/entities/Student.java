package nl.tudelft.oopp.demo.entities;

public class Student extends User {

    public Student(String username, long roomId) {
        super(username, roomId);
    }

    public Student(long roomId) {
        super(roomId);
    }

    @Override
    public String toString() {
        return "Student " + super.getNickname() + " in room " + super.getRoomId();
    }
}
