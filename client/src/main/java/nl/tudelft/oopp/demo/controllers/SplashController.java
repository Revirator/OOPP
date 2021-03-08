package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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

    @FXML
    private Text courseName;

    @FXML
    private Text startingTime;

    @FXML
    private Text startingDate;

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
                if (!code.contains("M") && room.getStartingTime().isBefore(LocalDateTime.now())) {

                    // The next few lines are to change the view to the room view
                    // Most of it is magic to me, but it works

                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl;

                    if (!code.contains("M")) {
                        xmlUrl = getClass().getResource("/studentRoom.fxml");
                    } else {
                        xmlUrl = getClass().getResource("/moderatorRoom.fxml");
                    }

                    loader.setLocation(xmlUrl);
                    Parent root = loader.load();

                    // Those lines pass the entered name and the room received
                    // from the DB to the next controller we will be using
                    if (!code.contains("M")) {
                        StudentRoomController src = loader.getController();
                        src.setData(name, room);
                    } else {
                        ModeratorRoomController mrc = loader.getController();
                        mrc.setData(name, room);
                    }

                    Stage stage = (Stage) anchor.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    // Here the view should change to the waiting room view instead

                    URL xmlUrl = getClass().getResource("/waitingRoom.fxml");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(xmlUrl);
                    Parent root = loader.load();
                    Stage stage = (Stage) anchor.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    Set<Node> set = root.lookupAll("Text");
                    for (Node node : set){
                        if(node.getId().equals("courseName")) {
                            courseName.setText(room.getRoomName());
                        }
                    }
                    courseName.setText(room.getRoomName());
                    //startingTime.setText("(" + room.getStartingTime().toString().substring(11,16) + ")");
                    //startingDate.setText(room.getStartingTime().toString().substring(0, 10).replace("-", "/"));
                }
            }
        }
    }
}
