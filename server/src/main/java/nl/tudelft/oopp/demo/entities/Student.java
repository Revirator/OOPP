package nl.tudelft.oopp.demo.entities;

public class Student extends User{
    public Student(String username, String roomCode) {
        super(username, roomCode);
    }

    public Student(String roomCode) {
        super(roomCode);
    }

    @Override
    public String toString() {
        return "Student " + super.getUsername() + " in room " + super.getRoomCode();
    }
}
