package nl.tudelft.oopp.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.ModeratorView;

public class ModeratorRoomController extends RoomController {

    @FXML
    private Button endLecture;

    @FXML
    private Label lectureName;

    @FXML
    private Label tooSlowLabel;

    @FXML
    private Label tooFastLabel;

    private ModeratorView moderatorView;

    /**
     * Used in SplashController to pass the user and the room object.
     * This method should be called after the fetch request so that it updates the information.
     * @param moderator - the moderator that is using the window
     * @param room - the room corresponding to the code entered
     * @param moderatorView - corresponding view to this controller (to add questions)
     */
    public void setData(User moderator, Room room, ModeratorView moderatorView) {
        super.setData(moderator, room, moderatorView);

        this.moderatorView = moderatorView;
        this.lectureName.setText(room.getRoomName());
        setFeedback();

//        // creates a service that allows a method to be called every timeframe
//        ScheduledService<Boolean> service = new ScheduledService<>() {
//            @Override
//            protected Task<Boolean> createTask() {
//                return new Task<>() {
//                    @Override
//                    protected Boolean call() {
//                        updateMessage("Checking for updates..");
//                        return true;
//                    }
//                };
//            }
//        };
//
//        // setting up and starting the thread
//        service.setPeriod(Duration.seconds(5));
//        service.setOnRunning(e -> {
//            roomRefresher();
//            questionRefresher();
//            participantRefresher();
//        });
//        service.start();
    }

   public void roomRefresher() {
       super.setRoom(ServerCommunication.getRoom(
               super.getRoom().getStudentsLink()));
       this.moderatorView.setData(super.getUser(), super.getRoom());
       setFeedback();
   }

    /**
     * Updates the feedback for the moderators.
     * For it to be done in real time it needs the fetch request.
     */
    public void setFeedback() {

        Room room = super.getRoom();

        tooSlowLabel.setText(Math.round(
                room.getPeopleThinkingLectureIsTooSlow() * 100
                        / room.getParticipants().size()
                        + room.getPeopleThinkingLectureIsTooSlow() * 100
                        % room.getParticipants().size()) + "%");

        if (Integer.parseInt(tooSlowLabel.getText().replace("%", "")) < 10) {
            tooSlowLabel.setTextFill(Paint.valueOf("DARKGREEN"));
        } else {
            tooSlowLabel.setTextFill(Paint.valueOf("RED"));
        }

        tooFastLabel.setText(Math.round(
                room.getPeopleThinkingLectureIsTooFast() * 100
                        / room.getParticipants().size()
                        + room.getPeopleThinkingLectureIsTooFast() * 100
                        % room.getParticipants().size()) + "%");

        if (Integer.parseInt(tooFastLabel.getText().replace("%", "")) < 10) {
            tooFastLabel.setTextFill(Paint.valueOf("DARKGREEN"));
        } else {
            tooFastLabel.setTextFill(Paint.valueOf("RED"));
        }
    }

    /** The method that is executed when the End lecture button is clicked.
     * Updates the status of the room to inactive so that new questions ..
     * .. and feedback are not processed.
     */
    public void endLecture() {

        Room room = super.getRoom();

        if (room == null || !room.isActive()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("The room does not exist or has ended already!");
            error.show();
            endLecture.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to end the lecture?");
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")) {
                ServerCommunication.updateRoomStatus(room.getModeratorLink());
                room.end();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setContentText("The lecture has ended successfully!");
                success.show();
                endLecture.setDisable(true);
            }
        }
    }

    /** The method is executed when the Export questions is clicked.
     * If the lecture hasn't ended the moderator is alerted about that.
     * Otherwise, a new window pops up and he can choose the file name ..
     * .. and directory to save the file with the questions.
     * The supported formats are .txt and .md .
     */
    public void exportQuestions() {

        Room room = super.getRoom();

        if (room.isActive()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please wait until the lecture has ended to export questions!");
            alert.show();
        } else {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser
                    .ExtensionFilter("Text Files (*.txt,*.md)", "*.txt", "*.md"));
            File selectedFile = fc.showSaveDialog(null);
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(selectedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            List<Question> answeredQuestions = ServerCommunication
                    .getAnsweredQuestions(room.getRoomId());
            for (int i = 0; i < answeredQuestions.size(); i++) {
                pw.println(answeredQuestions.get(i).toString());
            }
            pw.close();
        }
    }

    /**
     * Set the answer on the server-side.
     * @param question answered question
     * @param answer answer
     * @return true if successful, false if not
     */
    public boolean setAnswer(Question question, String answer) {

        if (answer.length() > 0) {

            if (!ServerCommunication.setAnswer(question.getId(), answer)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Server error!");
                alert.show();
                return false;
            }
            return true;
        }
        return false;
    }
}
