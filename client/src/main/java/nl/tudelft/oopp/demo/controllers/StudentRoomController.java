package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.data.Room;

public class StudentRoomController {

    @FXML
    private Button submit;

    @FXML
    private TextArea question;

    @FXML
    private AnchorPane anchor;

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

    public void submitQuestion() {
        if(this.room.isActive()) {
            if(question.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You need to enter a question to submit it!");
                alert.show();
            }
            else {
                // TODO: Add question to DB and display it to all users
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The lecture is over. You cannot ask questions anymore!");
            alert.show();
            question.setDisable(true);
            submit.setDisable(true);
        }
    }

    public void Test() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The lecture has ended!");
        alert.show();

        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
    }
}
