package nl.tudelft.oopp.demo.entities;

public class Moderator extends User {

    public Moderator(String username, long roomId) {
        super(username, roomId);
    }

    public Moderator(long roomId) {
        super(roomId);
    }

    @Override
    public String toString() {
        return "TA/Lecturer " + super.getNickname() + " in room " + super.getRoomId();
    }
}
