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
        if(nickName.getText().equals("") || link.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both nickname and link.");
            alert.show();
        } else {
            Room room = ServerCommunication.getRoom(link.getText());

            // Using alert temporary until the other features ar implemented
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(room == null) {
                alert.setContentText("Invalid room link.");
                link.clear();
                alert.show();
            }
            else if(room.isActive()) {
                alert.setContentText("You joined " + room.getRoomName());
                alert.show();   // Here the view should change to the room view
            }
            else {
                alert.setContentText("The room is not open yet.");
                alert.show();
            }
        }
    }
}
