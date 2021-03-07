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
import nl.tudelft.oopp.demo.data.Room;


public class SplashController {

    @FXML
    private TextField nickName;     // the value of the nickname text box

    @FXML
    private TextField link;     // the value of the link text box

    @FXML
    private AnchorPane anchor;      // the splash.fxml anchor pane

    @FXML
    private DatePicker date;    // the value of date user enters

    @FXML
    private TextField hour;     // the value of hour user enters

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
                    // Most of it is magic to me, but it works

                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl;

                    if (isStudent(code)) {
                        xmlUrl = getClass().getResource("/studentRoom.fxml");
                    } else {
                        xmlUrl = getClass().getResource("/moderatorRoom.fxml");
                    }

                    loader.setLocation(xmlUrl);
                    Parent root = loader.load();

                    // Those lines pass the entered name and the room received
                    // from the DB to the next controller we will be using
                    if (isStudent(code)) {
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
                    alert.setContentText("The room is not open yet.");
                    alert.show();
                }
            }
        }
    }

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

    /**
     * Returns if the room code is for student.
     * @param code the room code
     * @return true if the code is for student
     */
    private static boolean isStudent(String code) {
        // If the code/link format changes this should be changed as well
        return code.charAt(code.length() - 1) == 'S';
    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

}
