package nl.tudelft.oopp.demo.controllers;

import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.ModeratorView;

public class ModeratorRoomController {

    @FXML
    private Button endLecture;
    private User moderator;
    private Room room;
    private ModeratorView moderatorView;

    /** Used in SplashController to pass the user and the room object.
     * @param moderator the moderator that is using the window
     * @param room the room corresponding to the code entered
     * @param moderatorView - corresponding view to this controller (to add questions)
     */
    public void setData(User moderator, Room room, ModeratorView moderatorView) {
        this.moderator = moderator;
        this.room = room;
        this.moderatorView = moderatorView;

        // Next 3 lines are to execute the question refreshing every X seconds
        Timer t = new Timer();
        QuestionRefresher st = new QuestionRefresher();
        t.schedule(st,0,5000);
    }

    // Used just by the timer to refresh the questions every X seconds
    public class QuestionRefresher extends TimerTask {

        public void run() {
            moderatorView.updateAnsweredList();
        }
    }

    /** The method that is executed when the End lecture button is clicked.
     */
    public void endLecture() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to end the lecture?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            if (room == null || !room.isActive()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("The room does not exist or has ended already!");
                error.show();
            }
            ServerCommunication.updateRoom(room.getModeratorLink().toString());
            room.end();
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("The lecture has ended successfully!");
            success.show();
            endLecture.setDisable(true);
        }
    }
}
