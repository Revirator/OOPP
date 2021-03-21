package nl.tudelft.oopp.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moderators")
public class Moderator extends User {


    public Moderator() {

    }

    public Moderator(String nickname, Room room) {
        super(nickname, room);
    }

    public Moderator(long id, String username, Room room) {
        super(id, username, room);
    }


    @Override
    public String toString() {
        return "Moderator " + super.getNickname() + " in room " + getRoom();
    }

}
