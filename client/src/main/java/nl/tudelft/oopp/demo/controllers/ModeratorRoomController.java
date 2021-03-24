package nl.tudelft.oopp.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
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

    private User moderator;
    private Room room;
    private ModeratorView moderatorView;


    /**
     * Used in SplashController to pass the user and the room object.
     * This method should be called after the fetch request so that it updates the information.
     * @param moderator - the moderator that is using the window
     * @param room - the room corresponding to the code entered
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
                    protected Boolean call() {
                        updateMessage("Checking for updates..");
                        return true;
                    }
                };
            }
        };

        // setting up and starting the thread
        service.setPeriod(Duration.seconds(5));
        service.setOnRunning(e -> {
            roomRefresher();
            questionRefresher();
            participantRefresher();
        });
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

    /**
     * Calls methods in ServerCommunication to get updated lists from the database.
     * Updates the user views (periodically called by refresher)
     */
    public void participantRefresher() {
        List<Student> studentList = ServerCommunication.getStudents(room.getRoomId());
        List<Moderator> moderatorList = ServerCommunication.getModerators(room.getRoomId());
        moderatorView.updateParticipants(studentList, moderatorList);

    }

    /** Updates the room object and the feedback by calling the getRoom() ..
     * .. method in ServerCommunication.
     */
    public void roomRefresher() {
        this.room = ServerCommunication.getRoom(room.getStudentsLink());
        this.moderatorView.setData(moderator, room);
        setFeedback();
    }

    /**
     * Updates the feedback for the moderators.
     * For it to be done in real time it needs the fetch request.
     */
    public void setFeedback() {
        tooSlowLabel.setText(Math.round(
                this.room.getPeopleThinkingLectureIsTooSlow() * 100
                        / this.room.getParticipants().size()
                        + this.room.getPeopleThinkingLectureIsTooSlow() * 100
                        % this.room.getParticipants().size()) + "%");

        if (Integer.parseInt(tooSlowLabel.getText().replace("%", "")) < 10) {
            tooSlowLabel.setTextFill(Paint.valueOf("DARKGREEN"));
        } else {
            tooSlowLabel.setTextFill(Paint.valueOf("RED"));
        }

        tooFastLabel.setText(Math.round(
                this.room.getPeopleThinkingLectureIsTooFast() * 100
                        / this.room.getParticipants().size()
                        + this.room.getPeopleThinkingLectureIsTooFast() * 100
                        % this.room.getParticipants().size()) + "%");

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
     * Deletes this question upon pressing "delete" or "mark as answered" buttons.
     * Based on id of this question.
     * @param questionToRemove - Question to be removed from database.
     */
    public boolean deleteQuestion(Question questionToRemove) {

        if (!ServerCommunication.deleteQuestion(questionToRemove.getId())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Server error!");
            alert.show();
            return false;
        }
        return true;

    }

    /**
     * Edits this question according to new text entered upon pressing:
     *  - "edit answer" button in QuestionCell
     *  - "edit answer" button in AnsweredCell
     * Based on id of this question.
     * @param questionToEdit - Question to edit content of in database.
     */
    public boolean editQuestion(Question questionToEdit, String update) {

        if (update.length() > 0) {

            questionToEdit.setText(update);

            if (!ServerCommunication.editQuestion(questionToEdit.getId(), update)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Server error!");
                alert.show();
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Sets answer to this question in db.
     * Based on id of this question.
     * @param question - Question to set answer of content of in database.
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
