package nl.tudelft.oopp.demo.entities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class Room {
    private final String roomCode; // primary key for the DB
    private final URL studentsLink;
    private final URL staffLink;
    private final LocalDateTime startingTime;
    private String roomName;
    private boolean ongoing;
    // List of Users > DB ?
    private List<User> participants;
    // Maybe ArrayList and sort by upvotes
    // Or not needed at all because we have the DB
    private Set<Question> questions; // ?

    public Room(String roomCode, LocalDateTime startingTime, String roomName) throws MalformedURLException {
        // Maybe the roomCode can also be generated
        this.roomCode = roomCode;
        // Some way to generate 2 links
        // Example:
        this.studentsLink = new URL("http://localhost:8080/rooms/" + roomCode + "S");
        this.staffLink = new URL("http://localhost:8080/rooms/" + roomCode + "TL");
        this.startingTime = startingTime;
        this.roomName = roomName;
        this.ongoing = false;
        this.participants = new ArrayList<>();
        this.questions = new HashSet<>();
    }

    public String getRoomCode() {
        return roomCode;
    }

    public URL getStudentsLink() {
        return studentsLink;
    }

    public URL getStaffLink() {
        return staffLink;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void hasEnded() {
        this.ongoing = false;
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
        // this.participants.sort();
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
        return isOngoing() == room.isOngoing() && getRoomCode().equals(room.getRoomCode()) && getStudentsLink().equals(room.getStudentsLink()) && getStaffLink().equals(room.getStaffLink()) && getStartingTime().equals(room.getStartingTime()) && getRoomName().equals(room.getRoomName()) && getParticipants().equals(room.getParticipants()) && getQuestions().equals(room.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomCode(), getStudentsLink(), getStaffLink(), getStartingTime(), getRoomName(), isOngoing(), getParticipants(), getQuestions());
    }

    @Override
    public String toString() {
        return "Room " + roomCode + (ongoing ? "" : " starting at " + startingTime);
    }
}
