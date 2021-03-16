package nl.tudelft.oopp.demo.data;

public class Student extends User {

    public Student(String username, Room room) {
        super(username, room);
    }

    @Override
    public String toString() {
        return "Student " + super.getNickname() + " in lecture " + super.getRoom().getRoomName();
    }
}

