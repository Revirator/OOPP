package nl.tudelft.oopp.demo.entities;

public class Moderator extends User {

    public Moderator(long id, String username, long roomId) {
        super(id, username, roomId);
    }

    public Moderator(long id, long roomId) {
        super(id, roomId);
    }

    @Override
    public String toString() {
        return "TA/Lecturer " + super.getUsername() + " in room " + super.getRoomId();
    }
}
