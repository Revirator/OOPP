package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class User {
    // However I dont think we should store them in a DB
    private long id;
    private String username;
    private String roomCode;

    public User(long id, String username, String roomCode) {
        this.id = id;
        this.username = username;
        this.roomCode = roomCode;
    }

    public User(long id, String roomCode) {
        this.id = id;
        this.username = "Anonymous";
        this.roomCode = roomCode;
    }

    public long getId() {
        return id;
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
        return getId() == user.getId() && getUsername().equals(user.getUsername()) && getRoomCode().equals(user.getRoomCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getRoomCode());
    }
}
