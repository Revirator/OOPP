package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Room;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

public class SplashController {

    @FXML
    private TextField nickName;     // the value of the nickname text box

    @FXML
    private TextField link;     // the value of the link text box

    @FXML
    private AnchorPane anchor;      // the splash.fxml anchor pane

    /**
     * Handles clicking the button.
     */
    public void buttonClicked(ActionEvent actionEvent) throws IOException {
        if(nickName.getText().equals("") || link.getText().equals("")) {    // Check if one of the fields is empty

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both nickname and link.");
            alert.show();

        } else {        // If not: try to get a room from the server

            Room room = ServerCommunication.getRoom(link.getText());

            // Using alert temporary until the other features are implemented
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            if(room == null) {
                alert.setContentText("Invalid room link.");     // The room is null when the code is invalid
                link.clear();
            } else {
                String role;
                if(isStudent(link.getText())) role = "student";
                else role = "moderator";

                if(room.getStartingTime().isBefore(LocalDateTime.now())) {   // Might need improvement but works for now
                    alert.setContentText("You joined " + room.getRoomName() + " as a " + role); // Here the view should change to the room view

                    // The next few lines are to change the view to the room view
                    // Most of it is magic to me ngl, but it works
                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl = getClass().getResource("/mainScene.fxml"); // mainScene.fxml is just a placeholder for the actual scene
                    loader.setLocation(xmlUrl);
                    Parent root = (Parent) loader.load();

                    Stage stage = (Stage) anchor.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert.setContentText("The room is not open yet."); // Here the view should change to the waiting room view instead
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
