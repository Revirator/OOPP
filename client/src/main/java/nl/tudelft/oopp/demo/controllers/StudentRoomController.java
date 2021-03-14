package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Room;

public class StudentRoomController {
    @FXML
    private Button tooSlowButton;

    @FXML
    private Button tooFastButton;

    @FXML
    private Button resetButton;

    private String name;
    private Room room;

    /**
     * Used in SplashController to pass the username and the room object.
     * @param name the name entered by the user in splash
     * @param room the room corresponding to the code entered
     */
    public void setData(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    /** Increments the peopleThinkingLectureIsTooSlow field in Room ..
     * .. both on the server and client side by one.
     */
    public void lectureTooSlow() {
        resetButton.setDisable(false);
        tooSlowButton.setDisable(true);
        tooFastButton.setVisible(false);
        // To be changed
        tooSlowButton.setStyle("-fx-border-color: #12de00; -fx-border-width: 3px;");
        ServerCommunication.sendFeedback(room.getStudentsLink(), "slow");
    }

    /** Increments the peopleThinkingLectureIsTooFast field in Room ..
     * .. both on the server and client side by one.
     */
    public void lectureTooFast() {
        resetButton.setDisable(false);
        tooSlowButton.setVisible(false);
        tooFastButton.setDisable(true);
        // To be changed
        tooFastButton.setStyle("-fx-border-color: #12de00; -fx-border-width: 3px;");
        ServerCommunication.sendFeedback(room.getStudentsLink(), "fast");
    }

    /** Decrements either peopleThinkingLectureIsTooSlow or ..
     * .. peopleThinkingLectureIsTooFast field in Room ..
     * .. both on the server and client side by one ..
     * .. depending on which button was previously pressed.
     */
    public void resetFeedback() {
        // next 2 lines are not recommended
        tooSlowButton.setStyle(null);
        tooFastButton.setStyle(null);
        resetButton.setDisable(true);
        if (tooSlowButton.isVisible() && !tooFastButton.isVisible()) {
            tooSlowButton.setDisable(false);
            tooFastButton.setVisible(true);
            ServerCommunication.sendFeedback(room.getStudentsLink(), "resetSlow");
        } else {
            tooFastButton.setDisable(false);
            tooSlowButton.setVisible(true);
            ServerCommunication.sendFeedback(room.getStudentsLink(), "resetFast");
        }
    }
}
