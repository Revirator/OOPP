package nl.tudelft.oopp.demo.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
    // these match the properties in Room entity on server
    // but we only include what we need on client (there will be more added when needed)
    // No setter necessary, since we don't update on client.

    private long roomId;
    private String roomName;
    private String studentsLink;
    private String moderatorLink;
    private LocalDateTime startingTime;
    private boolean active;
    private List<User> participants;
    private List<Student> students;
    private List<Moderator> moderators;
    private int peopleThinkingLectureIsTooFast;
    private int peopleThinkingLectureIsTooSlow;

    /**
     * Room constructor.
     * @param roomName roomName
     * @param studentsLink studentsLink
     * @param moderatorLink moderatorLink
     * @param startingTime startingTime
     */
    public Room(long id, String studentsLink, String moderatorLink,
                LocalDateTime startingTime, String roomName,
                boolean active, List<User> participants, int slow, int fast) {
        this.roomId = id;
        this.studentsLink = studentsLink;
        this.moderatorLink = moderatorLink;
        this.startingTime = startingTime;
        this.roomName = roomName;
        this.active = active;
        this.participants = participants;
        this.students = new ArrayList<>();
        this.moderators = new ArrayList<>();
        this.peopleThinkingLectureIsTooSlow = slow;
        this.peopleThinkingLectureIsTooFast = fast;
    }

    /**
     * Room constructor.
     * @param roomName roomName
     * @param startingTime startingTime
     * @param active active
     */
    public Room(String roomName, LocalDateTime startingTime, boolean active) {
        this.roomName = roomName;
        this.startingTime = startingTime;
        this.active = active;
        this.participants = new ArrayList<>();
        this.students = new ArrayList<>();
        this.moderators = new ArrayList<>();
        this.peopleThinkingLectureIsTooSlow = 0;
        this.peopleThinkingLectureIsTooFast = 0;
    }

    /**
     * Room constructor.
     * @param roomName roomName
     * @param startingTime startingTime
     * @param active active
     */
    public Room(long roomId, String roomName, LocalDateTime startingTime, boolean active) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.startingTime = startingTime;
        this.active = active;
        this.participants = new ArrayList<>();
        this.students = new ArrayList<>();
        this.moderators = new ArrayList<>();
        this.peopleThinkingLectureIsTooSlow = 0;
        this.peopleThinkingLectureIsTooFast = 0;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getStudentsLink() {
        return studentsLink;
    }

    public String getModeratorLink() {
        return moderatorLink;
    }

    public long getRoomId() {
        return roomId;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public boolean isActive() {
        return active;
    }

    public int getPeopleThinkingLectureIsTooFast() {
        return peopleThinkingLectureIsTooFast;
    }

    public int getPeopleThinkingLectureIsTooSlow() {
        return peopleThinkingLectureIsTooSlow;
    }

    public void end() {
        this.active = false;
    }

    public List<User> getParticipants() {
        return this.participants;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<Moderator> getModerators() {
        return this.moderators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return isActive() == room.isActive()
                && getPeopleThinkingLectureIsTooFast() == room.getPeopleThinkingLectureIsTooFast()
                && getPeopleThinkingLectureIsTooSlow() == room.getPeopleThinkingLectureIsTooSlow()
                && getRoomName().equals(room.getRoomName())
                && getStudentsLink().equals(room.getStudentsLink())
                && getModeratorLink().equals(room.getModeratorLink())
                && getStartingTime().equals(room.getStartingTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomId(), getRoomName(), getStudentsLink(), getModeratorLink(),
                getStartingTime(), isActive(), getParticipants(), getStudents(), getModerators(),
                getPeopleThinkingLectureIsTooFast(), getPeopleThinkingLectureIsTooSlow());
    }
}
