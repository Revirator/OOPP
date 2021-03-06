package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Room;


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

        // Check if one of the fields is empty
        if (nickName.getText().equals("") || link.getText().equals("")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both nickname and link.");
            alert.show();

        } else {        // If not: try to get a room from the server
            String name = nickName.getText();
            String code = link.getText();
            Room room = ServerCommunication.getRoom(code);

            // Using alert temporary until the other features are implemented
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            if (room == null) {     // The room is null when the code is invalid
                alert.setContentText("Invalid room link.");
                link.clear();
                alert.show();
            } else {
                // This check might need improvements but works for now
                if (room.getStartingTime().isBefore(LocalDateTime.now())) {

                    // The next few lines are to change the view to the room view
                    // Most of it is magic to me ngl, but it works

                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl;

                    if (isStudent(code)) {
                        xmlUrl = getClass().getResource("/studentRoom.fxml");
                    } else {
                        xmlUrl = getClass().getResource("/moderatorRoom.fxml");
                    }

                    loader.setLocation(xmlUrl);
                    Parent root = loader.load();

                    StudentSceneController ssc = loader.getController();
                    ssc.setUserName(name);

                    // Somewhere here should be some code
                    // that passes arguments(room, code) to the new view

                    Stage stage = (Stage) anchor.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    // Here the view should change to the waiting room view instead
                    alert.setContentText("The room is not open yet.");
                    alert.show();
                }
            }
        }
    }

    /**
     * Returns if the room code is for student.
     * @param code the room code
     * @return true if the code is for student
     */
    private static boolean isStudent(String code) {
        // If the code/link format changes this should be changed as well
        return code.charAt(code.length() - 1) == 'S';
    }
}
