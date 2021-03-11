package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import nl.tudelft.oopp.demo.data.Room;

public class StudentRoomController {

    @FXML
    private Button submit;

    @FXML
    private TextArea question;

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
            if(question.textProperty().equals("Type your question here...")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You need to enter a question to submit it!");
                alert.show();
            }
        }
        else {
            // It can be either this code or disabling the button
            // Personally I like this more
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The lecture is over. You cannot ask questions anymore!");
            alert.show();
            question.setDisable(true);
            submit.setDisable(true);
        }
    }
}
