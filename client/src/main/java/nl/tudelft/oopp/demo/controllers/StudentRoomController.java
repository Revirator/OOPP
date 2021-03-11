package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
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

    /** The method is executed when the submit question button is pressed.
     *  If the room is not active - the student sees an alert of type warning.
     *  If the room is active but the question form is blank - ..
     *  .. they see an alert of type error.
     *  Else the question is sent to the server via a POST request.
     */
    public void submitQuestion() {
        if (this.room.isActive()) {
            if (question.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You need to enter a question to submit it!");
                alert.show();
            } else {
                // TODO: Add question to DB and display it to all users
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The lecture is over. You cannot ask questions anymore!");
            alert.show();
            question.setDisable(true);
            submit.setDisable(true);
        }
    }

    /** Still a test class. The idea is that the students ..
     * .. are alerted that the room has been closed.
     */
    public void test() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The lecture has ended!");
        alert.show();
    }
}
