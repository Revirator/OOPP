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
        if(nickName.getText().equals("") || link.getText().equals("")) {    // Check if one of the fields is empty

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both nickname and link.");
            alert.show();

        } else {        // If not: try to get a room from the server

            Room room = ServerCommunication.getRoom(link.getText());

            // Using alert temporary until the other features are implemented
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            if(room == null) {
                alert.setContentText("Invalid room link.");
                link.clear();
            } else {
                String role;
                if(isStudent(link.getText())) role = "student";
                else role = "moderator";

                if(room.isActive()) {   // Here there has to be proper check for if the room is currently open but
                    alert.setContentText("You joined " + room.getRoomName() + " as a " + role); // Here the view should change to the room view
                } else {
                    alert.setContentText("The room is not open yet."); // Here the view should change to the waiting room view
                }
            }
            alert.show();
        }
    }

    /**
     * Returns if the room code is for student
     * @param code the room code
     * @return true if the code is for student
     */
    private static boolean isStudent(String code) {
        return code.charAt(code.length() - 1) == 'S';   // If the code/link format changes this should be changed as well
    }
}
