package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.data.Room;

public class ModeratorRoomController {

    @FXML
    private AnchorPane anchor = new AnchorPane();

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

    public void endLecture() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to end the lecture?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK")) {
            room.hasEnded();
            // maybe close the window only for the students
            // and show an alert to the moderators that the lecture has ended successfully
            Stage stage = (Stage) anchor.getScene().getWindow();
            stage.close();
        }
    }
}
