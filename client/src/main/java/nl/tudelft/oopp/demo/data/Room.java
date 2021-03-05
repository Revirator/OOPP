package nl.tudelft.oopp.demo.data;

import java.net.URL;
import java.time.LocalDateTime;

public class Room {
    // these match the properties in Room entity on server
    // but we only include what we need on client (there will be more added when needed)
    // No setter necessary, since we don't update on client.

    private String roomName;
    private URL studentsLink;
    private URL moderatorLink;
    private LocalDateTime startingTime;
    private boolean active;

    public Room(String roomName, URL studentsLink, URL moderatorLink, LocalDateTime startingTime, boolean active) {
        this.roomName = roomName;
        this.studentsLink = studentsLink;
        this.moderatorLink = moderatorLink;
        this.startingTime = startingTime;
        this.active = active;
    }

    public String getRoomName() {
        return roomName;
    }

    public URL getStudentsLink() {
        return studentsLink;
    }

    public URL getModeratorLink() {
        return moderatorLink;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public boolean isActive() {
        return active;
    }
}
