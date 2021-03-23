package nl.tudelft.oopp.demo.data;

public class Moderator extends User {

    public Moderator(Long id, String nickname, Room room) {
        super(id, nickname, room);
    }

    public Moderator(String username, Room room) {
        super(username, room);
    }

    @Override
    public String getRole() {
        return super.getRole();
    }

    @Override
    public String toString() {
        return "Moderator " + super.getNickname() + " in room " + super.getRoom().getRoomName();
    }
}
