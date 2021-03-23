package nl.tudelft.oopp.demo.data;

public class Student extends User {

    public Student(Long id, String nickname, Room room) {
        super(id, nickname, room);
    }

    public Student(String username, Room room) {
        super(username, room);
    }

    @Override
    public String getRole() {
        return super.getRole();
    }

    @Override
    public String toString() {
        return "Student " + super.getNickname() + " in lecture " + super.getRoom().getRoomName();
    }
}

