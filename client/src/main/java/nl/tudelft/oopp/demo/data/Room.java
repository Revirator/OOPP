package nl.tudelft.oopp.demo.data;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Room {
    // these match the properties in Room entity on server
    // but we only include what we need on client (there will be more added when needed)
    // No setter necessary, since we don't update on client.

    private long roomId;
    private String roomName;
    private URL studentsLink;
    private URL moderatorLink;
    private LocalDateTime startingTime;
    private boolean active;
    private List<User> participants;
    private int peopleThinkingLectureIsTooFast;
    private int peopleThinkingLectureIsTooSlow;

    /**
     * Room constructor.
     * @param roomName roomName
     * @param studentsLink studentsLink
     * @param moderatorLink moderatorLink
     * @param startingTime startingTime
     */
    public Room(long id, URL studentsLink, URL moderatorLink,
                LocalDateTime startingTime, String roomName,
                boolean active, List<User>  participants, int slow, int fast) {
        this.roomId = id;
        this.studentsLink = studentsLink;
        this.moderatorLink = moderatorLink;
        this.startingTime = startingTime;
        this.roomName = roomName;
        this.active = active;
        this.participants = participants;
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
        // or
        // this.participants = participants;
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
        // or
        // this.participants = participants;
        this.peopleThinkingLectureIsTooSlow = 0;
        this.peopleThinkingLectureIsTooFast = 0;
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

    public void votedTooSlow() {
        this.peopleThinkingLectureIsTooSlow++;
    }

    public void votedTooFast() {
        this.peopleThinkingLectureIsTooFast++;
    }

    /** Decrements one of the fields depending on the feedback received.
     * @param condition feedback
     */
    public void resetVote(String condition) {
        if (condition.equals("resetSlow")) {
            peopleThinkingLectureIsTooSlow--;
        }
        if (condition.equals("resetFast")) {
            peopleThinkingLectureIsTooFast--;
        }
    }

    public void end() {
        this.active = false;
    }

    /** Should be a getter. Doesn't work for now.
     * @return the list of participants
     */
    public List<User> getParticipants() {
        // Used for testing purposes at the moment
        ArrayList<User> test = new ArrayList<>();
        test.add(new Moderator("TEST1",this));
        test.add(new Student("TEST2",this));
        return  test;
        // should be
        // return this.participants;
        // but this is always returning null atm cause of the server
    }

    public void addParticipant(User user) {
        this.participants.add(user);
    }
}
