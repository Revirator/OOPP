package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.data.Room;

public class WaitingRoomController {

    @FXML
    private AnchorPane anchorWaitingRoom;

    @FXML
    private Text courseName;

    @FXML
    private Text startingTime;

    @FXML
    private Text startingDate;

    private String name;
    private Room room;

    public void setData(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    /** Loads the room information to the waiting room.
     * The waiting room is visible until the lecture starts.
     * @param args - Not used
     */
    public void main(String[] args) {
        courseName.setText(room.getRoomName());
        startingTime.setText("(" + room.getStartingTime().toString().substring(11, 16) + ")");
        startingDate.setText(room.getStartingTime().toString()
                .substring(0, 10).replace("-", "/"));

        double diff = room.getStartingTime().toEpochSecond(ZoneOffset.ofHours(0))
                - LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));

        if (diff > 0) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(diff), e -> loadStudentRoom()));
            timeline.play();
        } else {
            loadStudentRoom();
        }
    }

    /** The method changes the view from the ..
     * .. waiting room to the student room and ..
     * .. sets up the next controller.
     */
    public void loadStudentRoom() {
        URL xmlUrl = getClass().getResource("/studentRoom.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(xmlUrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong.");
            alert.show();
            return;
        }

//        StudentRoomController src = loader.getController();
//        src.setData(name, room);

        Stage stage = (Stage) anchorWaitingRoom.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
