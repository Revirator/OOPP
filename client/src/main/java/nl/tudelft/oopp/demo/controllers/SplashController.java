package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.ModeratorView;
import nl.tudelft.oopp.demo.views.StudentView;


public class SplashController {

    @FXML
    private TextField nickName;     // the value of the nickname text box

    @FXML
    private TextField link;     // the value of the link text box

    @FXML
    private TextField roomName;     //the value of the room name text box

    @FXML
    private AnchorPane anchor;      // the splash.fxml anchor pane

    @FXML
    private DatePicker date;    // the value of date user enters

    @FXML
    private TextField hour;     // the value of hour user enters

    /**
     * Handles clicking the "join room" button.
     */
    public void buttonClicked(ActionEvent actionEvent) {

        // Check if one of the fields is empty
        if (nickName.getText().equals("") || link.getText().equals("")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both nickname and link.");
            alert.show();

        } else {        // If not: try to get a room from the server
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
                // If you are a Moderator you don't have to wait in the waiting room
                if (code.contains("M") || room.getStartingTime().isBefore(LocalDateTime.now())) {
                    if (code.contains("M")) {
                        User moderator = new Moderator(nickName.getText(), room);
                        ModeratorView moderatorView = new ModeratorView();
                        moderatorView.setData(moderator, room);
                        moderatorView.start((Stage) anchor.getScene().getWindow());
                    } else {
                        User student = new Student(nickName.getText(), room);
                        StudentView studentView = new StudentView();
                        studentView.setData(student, room);
                        studentView.start((Stage) anchor.getScene().getWindow());
                    }
                } else {
                    // Here the view should change to the waiting room view instead

                    URL xmlUrl = getClass().getResource("/waitingRoom.fxml");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(xmlUrl);
                    Parent root = null;

                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setContentText("Something went wrong! Could not load the room");
                        error.show();
                    }

                    Stage stage = (Stage) anchor.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    User student = new Student(nickName.getText(), room);
                    WaitingRoomController waitingRoomController = loader.getController();
                    waitingRoomController.setData(student, room);
                    waitingRoomController.main(new String[0]);
                }
            }
        }
    }

    /**
     * Handles clicking the "create instant room" button.
     */
    public void startRoom(ActionEvent actionEvent) throws IOException {

        if (roomName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter name of room");
            alert.show();
        } else {
            // create new room that's immediately active
            Room newRoom = new Room(roomName.getText(), LocalDateTime.now(), true);
            newRoom = ServerCommunication.makeRoom(newRoom);

            Stage primaryStage = (Stage) anchor.getScene().getWindow();

            User moderator = new Moderator(nickName.getText(), newRoom);
            ModeratorView moderatorView = new ModeratorView();
            moderatorView.setData(moderator, newRoom);
            moderatorView.start(primaryStage);
        }
    }

    /** Checkstyle wants a comment - to be edited.
     * @param actionEvent - to be edited
     * @throws IOException - to be edited
     */
    public void scheduleRoom(ActionEvent actionEvent) throws IOException {
        if (date == null || hour == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both nickname and link.");
            alert.show();
        } else {
            LocalDate localDate = date.getValue();
            String strHour = hour.getText(0,1);
            String strMin = hour.getText(3,4);
            int intHour = Integer.parseInt(strHour);
            int intMin = Integer.parseInt((strMin));
            LocalTime localTime = LocalTime.of(intHour, intMin);
            LocalDateTime targetTime = LocalDateTime.of(localDate, localTime);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    //TODO open the room lol
                }
            }, convertToDateViaSqlTimestamp(targetTime));
        }
    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

}
