package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table
public class Room {

    // transient = no column in DB

    @Id
    @SequenceGenerator(
            name = "room_sequence",
            sequenceName = "room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy =GenerationType.SEQUENCE,
            generator = "room_sequence"
    )
    private long roomId;
    private String studentsLink;
    private String moderatorLink;
    private LocalDateTime startingTime;
    private String roomName;                    // course name e.g.
    @Transient
    private boolean active;
    @Transient
    private List<User> participants;            // List of Users > DB ?
    @Transient
    private Set<Question> questions;          // Or not needed at all because we have the DB


    public Room() {

    }

    public Room(long id, LocalDateTime startingTime, String roomName) {
        // Maybe the roomCode can also be generated
        this.roomId = id;
        // Some way to generate 2 links
        //this.studentsLink = ;
        //this.moderatorLink = ;
        this.startingTime = startingTime;
        this.roomName = roomName;
        this.active = false;
        this.participants = new ArrayList<>();
        this.questions = new HashSet<>();
    }


    public long getRoomId() {
        return roomId;
    }

    public String getStudentsLink() {
        return studentsLink;
    }

    public String getModeratorLink() {
        return moderatorLink;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    // ?
    public String getRoomName() {
        return roomName;
    }

    // ?
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isActive() {
        return active;
    }

    public void hasEnded() {
        this.active = false;
        // A function that closes the window for the students, etc. ...
        while(!this.participants.isEmpty()) this.participants.remove(0);
    }

    // ?
    public List<User> getParticipants() {
        return participants;
    }

    // Useful for exporting the questions ?
    public Set<Question> getQuestions() {
        return questions;
    }

    public void addParticipant(User user) {
        this.participants.add(user);
        // Sort by nicknames
        //this.participants.sort();
    }

    public void removeParticipant (User user) {
        this.participants.remove(user);
    }

    public void addQuestion (Question question) {
        this.questions.add(question);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return isActive() == room.isActive() && getRoomId() == room.getRoomId() && getStudentsLink().equals(room.getStudentsLink()) && getModeratorLink().equals(room.getModeratorLink()) && getStartingTime().equals(room.getStartingTime()) && getRoomName().equals(room.getRoomName()) && getParticipants().equals(room.getParticipants()) && getQuestions().equals(room.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomId(), getStudentsLink(), getModeratorLink(), getStartingTime(), getRoomName(), isActive(), getParticipants(), getQuestions());
    }

    @Override
    public String toString() {
        return "Room " + roomId + (active ? "" : " starting at " + startingTime);
    }
}
