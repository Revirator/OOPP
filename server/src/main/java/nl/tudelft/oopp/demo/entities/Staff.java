package nl.tudelft.oopp.demo.entities;

public class Staff extends User{
    public Staff(long id, String username, String roomCode) {
        super(id, username, roomCode);
    }

    public Staff(long id, String roomCode) {
        super(id, roomCode);
    }

    @Override
    public String toString() {
        return "TA/Lecturer " + super.getUsername() + " in room " + super.getRoomCode();
    }
}
