package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public abstract class User {

    private String nickname;
    private long roomId;

    public User(String nickname, long roomId) {
        this.nickname = nickname;
        this.roomId = roomId;
    }

    public User(long roomId) {
        this.nickname = "Anonymous";
        this.roomId = roomId;
    }

    public String getNickname() {
        return nickname;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
        return getNickname().equals(user.getNickname())
                && getRoomId() == user.getRoomId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickname(), getRoomId());
    }
}
