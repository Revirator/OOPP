package nl.tudelft.oopp.demo.data;

import java.util.Objects;

public class User {

    private String nickname;
    private Room room;

    public User(String nickname, Room room) {
        this.nickname = nickname;
        this.room = room;
    }

    public String getNickname() {
        return nickname;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return getNickname().equals(user.getNickname()) && room.equals(user.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickname(), room);
    }
}

