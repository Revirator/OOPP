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

    public void lectureTooSlow() {
        resetButton.setDisable(false);
        tooSlowButton.setDisable(true);
        tooFastButton.setDisable(true);
        // To be changed
        tooSlowButton.setStyle("-fx-border-color: #FF0000;");
        ServerCommunication.sendFeedback("slow");
    }

    public void lectureTooFast() {
        resetButton.setDisable(false);
        tooSlowButton.setDisable(true);
        tooFastButton.setDisable(true);
        // To be changed
        tooFastButton.setStyle("-fx-border-color: #FF0000;");
        ServerCommunication.sendFeedback("fast");
    }

    public void resetFeedback() {
        // line 49 and 50 not recommended
        tooSlowButton.setStyle(null);
        tooFastButton.setStyle(null);
        tooSlowButton.setDisable(false);
        tooFastButton.setDisable(false);
        resetButton.setDisable(true);
        ServerCommunication.sendFeedback("reset");
    }
}
