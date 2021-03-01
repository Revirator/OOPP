package nl.tudelft.oopp.demo.entities;

public class Student extends User{
    public Student(long id, String username, String roomCode) {
        super(id, username, roomCode);
    }

    public Student(long id, String roomCode) {
        super(id, roomCode);
    }

    @Override
    public String toString() {
        return "Student " + super.getUsername() + " in room " + super.getRoomCode();
    }
}
