package nl.tudelft.oopp.demo.entities;

public class Staff extends User{
    public Staff(String username, String roomCode) {
        super(username, roomCode);
    }

    public Staff(String roomCode) {
        super(roomCode);
    }

    @Override
    public String toString() {
        return "TA/Lecturer " + super.getUsername() + " in room " + super.getRoomCode();
    }
}
