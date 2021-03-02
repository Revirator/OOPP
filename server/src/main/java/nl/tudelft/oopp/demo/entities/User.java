package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class User {
    private String username;
    private String roomCode;

    public User(String username, String roomCode) {
        this.username = username;
        this.roomCode = roomCode;
    }

    public User(String roomCode) {
        this.username = "Anonymous";
        this.roomCode = roomCode;
    }

    public String getUsername() {
        return username;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername()) && getRoomCode().equals(user.getRoomCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getRoomCode());
    }
}
