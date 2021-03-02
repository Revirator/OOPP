package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public abstract class User {
    // However I dont think we should store them in a DB
    private long id;
    private String username;
    private long roomId;

    public User(long id, String username, long roomId) {
        this.id = id;
        this.username = username;
        this.roomId = roomId;
    }

    public User(long id, long roomId) {
        this.id = id;
        this.username = "Anonymous";
        this.roomId = roomId;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && getUsername().equals(user.getUsername()) && getRoomId() == user.getRoomId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getRoomId());
    }
}
