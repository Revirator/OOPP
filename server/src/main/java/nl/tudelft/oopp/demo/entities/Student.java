package nl.tudelft.oopp.demo.entities;

public class Student extends User {

    public Student(long id, String username, long roomId) {
        super(id, username, roomId);
    }

    public Student(long id, long roomId) {
        super(id, roomId);
    }

    @Override
    public String toString() {
        return "Student " + super.getUsername() + " in room " + super.getRoomId();
    }
}
