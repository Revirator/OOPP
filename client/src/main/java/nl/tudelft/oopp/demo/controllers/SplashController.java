package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Room;

import java.io.IOException;

public class SplashController {

    @FXML
    private TextField nickName;     // the value of the nickname text box

    @FXML
    private TextField link;     // the value of the link text box

    /**
     * Handles clicking the button.
     */
    public void buttonClicked(ActionEvent actionEvent) {
        Room room = ServerCommunication.getRoom(link.getText());

        // Using alert temporary just to test
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("You are in room: " + room.getRoomName());
        alert.show();
    }
}
