package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
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

    /** The method that is executed when the End lecture button is clicked.
     */
    public void endLecture() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to end the lecture?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            if (room == null) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("The room does not exist");
                error.show();
            }
            ServerCommunication.updateRoom(room.getModeratorLink().toString());
            room.hasEnded();
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("The lecture has ended successfully!");
            success.show();
        }
    }
}
