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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Moderator)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Moderator student = (Moderator) o;
        return getId().equals(student.getId());
    }

    @Override
    public String toString() {
        return "Moderator " + super.getNickname() + " in room " + getRoomId();
    }

}
