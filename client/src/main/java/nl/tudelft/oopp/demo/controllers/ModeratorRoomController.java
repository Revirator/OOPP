package nl.tudelft.oopp.demo.controllers;

import java.util.List;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.ModeratorView;
import org.springframework.scheduling.annotation.Scheduled;

public class ModeratorRoomController {

    @FXML
    private Button endLecture;

    @FXML
    private Label lectureName;

    @FXML
    private  Label tooSlowLabel;

    @FXML
    private Label tooFastLabel;

    private User moderator;
    private Room room;
    private ModeratorView moderatorView;


    /** Used in SplashController to pass the user and the room object.
     * This method should be called after the fetch request so that it updates the information.
     * @param moderator the moderator that is using the window
     * @param room the room corresponding to the code entered
     * @param moderatorView - corresponding view to this controller (to add questions)
     */
    public void setData(User moderator, Room room, ModeratorView moderatorView) {
        this.moderator = moderator;
        this.room = room;
        this.moderatorView = moderatorView;
        this.lectureName.setText(this.room.getRoomName());
        setFeedback();

        // creates a service that allows a method to be called every timeframe
        ScheduledService<Boolean> service = new ScheduledService<>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<>() {
                    @Override
                    protected  Boolean call() {
                        updateMessage("Checking for updates..");
                        return true;
                    }
                };
            }
        };

        // setting up and starting the thread
        service.setPeriod(Duration.seconds(5));
        service.setOnRunning(e -> questionRefresher());
        service.start();
    }

    /**
     * Calls methods in ServerCommunication to get updated lists from the database.
     * Updates the actual view.
     */
    public void questionRefresher() {
        List<Question> questionList = ServerCommunication.getQuestions(room.getRoomId());
        List<Question> answeredList = ServerCommunication.getAnsweredQuestions(room.getRoomId());
        moderatorView.update(questionList, answeredList);
    }


    /** Updates the feedback for the moderators.
     * For it to be done in real time it needs the fetch request.
     */
    public void setFeedback() {
        tooSlowLabel.setText(Math.round(
                this.room.getPeopleThinkingLectureIsTooSlow() * 100
                        / this.room.getParticipants().size()
                        + this.room.getPeopleThinkingLectureIsTooSlow() * 100
                        % this.room.getParticipants().size()) + "%");

        if (Integer.parseInt(tooSlowLabel.getText().replace("%","")) < 10) {
            tooSlowLabel.setTextFill(Paint.valueOf("DARKGREEN"));
        } else {
            tooSlowLabel.setTextFill(Paint.valueOf("RED"));
        }

        tooFastLabel.setText(Math.round(
                this.room.getPeopleThinkingLectureIsTooFast() * 100
                        / this.room.getParticipants().size()
                        + this.room.getPeopleThinkingLectureIsTooFast() * 100
                        % this.room.getParticipants().size()) + "%");

        if (Integer.parseInt(tooFastLabel.getText().replace("%","")) < 10) {
            tooFastLabel.setTextFill(Paint.valueOf("DARKGREEN"));
        } else {
            tooFastLabel.setTextFill(Paint.valueOf("RED"));
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
