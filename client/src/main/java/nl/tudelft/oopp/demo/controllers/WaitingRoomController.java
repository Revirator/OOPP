package nl.tudelft.oopp.demo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.StudentView;

public class WaitingRoomController {

    @FXML
    private AnchorPane anchorWaitingRoom;

    @FXML
    private Text courseName;

    @FXML
    private Text startingTime;

    @FXML
    private Text startingDate;

    private User student;
    private Room room;

    /** Used in SplashController to pass the user and the room object.
     * @param student the moderator that is using the window
     * @param room the room corresponding to the code entered
     */
    public void setData(User student, Room room) {
        this.student = student;
        this.room = room;
    }

    /** Loads the room information to the waiting room.
     * The waiting room is visible until the lecture starts.
     * @param args - Not used
     */
    public void main(String[] args) {
        courseName.setText(room.getRoomName());
        startingTime.setText("(" + room.getStartingTime().toString().substring(11, 16) + ")");
        startingDate.setText(room.getStartingTime().toString()
                .substring(0, 10).replace("-", "/"));

        double diff = room.getStartingTime().toEpochSecond(ZoneOffset.ofHours(0))
                - LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));

        if (diff > 0) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(diff), e -> loadStudentRoom()));
            timeline.play();
        } else {
            loadStudentRoom();
        }
    }

    /** The method changes the view from the ..
     * .. waiting room to the student room and ..
     * .. sets up the next controller.
     */
    public void loadStudentRoom() {
        StudentView studentView = new StudentView();
        studentView.setData(student, room);
        studentView.start((Stage) anchorWaitingRoom.getScene().getWindow());
    }

    /**
     * Used for testing.
     * @return current student
     */
    public User getStudent() {
        return student;
    }

    /**
     * Used for testing.
     * @return current room
     */
    public Room getRoom() {
        return room;
    }
}
