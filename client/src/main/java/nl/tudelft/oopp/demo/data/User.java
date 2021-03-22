package nl.tudelft.oopp.demo.data;

import java.util.Objects;

public class User {

    private long id;
    private String nickname;
    private Room room;

    /** Initializes a new instance of a User with the parameters provided.
     * Used by ServerCommunication when updating the Student.
     * @param id the id of the user in the db
     * @param nickname the nickname of the user
     * @param room the room the user is in
     */
    public User(Long id, String nickname, Room room) {
        this.id = id;
        this.nickname = nickname;
        this.room = room;
    }

    public User(String nickname, Room room) {
        this.nickname = nickname;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public Room getRoom() {
        return room;
    }

    public String getRole() {
        return "User";
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

