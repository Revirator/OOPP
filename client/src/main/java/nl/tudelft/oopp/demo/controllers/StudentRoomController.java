package nl.tudelft.oopp.demo.controllers;

import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.StudentView;

public class StudentRoomController {
    @FXML
    private Button tooSlowButton;

    @FXML
    private Button tooFastButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button submit;

    @FXML
    private TextArea questionBox;

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label lectureName;

    private User student;
    private Room room;
    private StudentView studentView;

    /** Used in SplashController to pass the user and the room object.
     * Data injected by start() in StudentView.
     * @param student the student that is using the window
     * @param room the room corresponding to the code entered
     * @param studentView - corresponding view to this controller (to add questions)
     */
    public void setData(User student, Room room, StudentView studentView) {
        this.student = student;
        this.room = room;
        this.studentView = studentView;
        this.lectureName.setText(this.room.getRoomName());

        // Next 3 lines are to execute the question refreshing every X seconds
        Timer t = new Timer();
        QuestionRefresher st = new QuestionRefresher();
        t.schedule(st,0,5000);
    }

    // Used just by the timer to refresh the questions every X seconds
    public class QuestionRefresher extends TimerTask {

        public void run() {
            studentView.updateAnsweredList();
        }
    }

    /** Callback method for "Submit" button in student room.
     * If the room is not active - the student sees an alert of type warning.
     * If the room is active but the question form is blank - ..
     * .. they see an alert of type error.
     * Else the question is sent to the server via a POST request.
     */
    public void submitQuestion() {
        if (this.room.isActive()) {
            if (questionBox.getText().length() < 7) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter at least 8 characters!");
                alert.show();
            } else {
                // Create new question, id returned by server (needed for delete/edit).
                Question newQuestion = new Question(this.room.getRoomId(), questionBox.getText(),
                        this.student.getNickname(), true);
                Long newId = ServerCommunication.postQuestion(newQuestion);
                newQuestion.setId(newId);

                questionBox.clear();
                this.studentView.addQuestion(newQuestion);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The lecture is over. You cannot ask questions anymore!");
            alert.show();
            questionBox.setDisable(true);
            submit.setDisable(true);
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
     * Edits this question according to new text entered upon pressing "edit" button.
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

    /** Increments the peopleThinkingLectureIsTooSlow field in Room ..
     * .. both on the server and client side by one.
     */
    public void lectureTooSlow() {
        if (!room.isActive()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("The lecture is over! You cannot send feedback anymore!");
            alert.show();
            tooSlowButton.setDisable(true);
            tooFastButton.setDisable(true);
        } else {
            resetButton.setDisable(false);
            tooSlowButton.setDisable(true);
            tooFastButton.setVisible(false);
            ServerCommunication.sendFeedback(room.getStudentsLink(), "slow");
        }
    }

    /** Increments the peopleThinkingLectureIsTooFast field in Room ..
     * .. both on the server and client side by one.
     */
    public void lectureTooFast() {
        if (!room.isActive()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("The lecture is over! You cannot send feedback anymore!");
            alert.show();
            tooFastButton.setDisable(true);
            tooSlowButton.setDisable(true);
        } else {
            resetButton.setDisable(false);
            tooSlowButton.setVisible(false);
            tooFastButton.setDisable(true);
            ServerCommunication.sendFeedback(room.getStudentsLink(), "fast");
        }
    }

    /** Decrements either peopleThinkingLectureIsTooSlow or ..
     * .. peopleThinkingLectureIsTooFast field in Room ..
     * .. both on the server and client side by one ..
     * .. depending on which button was previously pressed.
     */
    public void resetFeedback() {
        if (!room.isActive()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("The lecture is over! You cannot send feedback anymore!");
            alert.show();
            tooFastButton.setVisible(true);
            tooSlowButton.setVisible(true);
            tooFastButton.setDisable(true);
            tooSlowButton.setDisable(true);
        } else {
            resetButton.setDisable(true);
            if (tooSlowButton.isVisible() && !tooFastButton.isVisible()) {
                tooSlowButton.setDisable(false);
                tooFastButton.setVisible(true);
                ServerCommunication.sendFeedback(room.getStudentsLink(), "resetSlow");
            } else {
                tooFastButton.setDisable(false);
                tooSlowButton.setVisible(true);
                ServerCommunication.sendFeedback(room.getStudentsLink(), "resetFast");
            }
        }
    }

    /** Alert displayed when lecture is inactive.
     *
     */
    public void lectureHasEnded() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The lecture has ended!");
        alert.show();
    }

    /** Increments the number of upvotes of this question by 1.
     * @param question - Question to upvote
     */
    public boolean upvoteQuestion(Question question) {

        // Check if user already voted on question
        if (question.voted()) {
            question.deUpvote();
            if (!ServerCommunication.deUpvoteQuestion(question.getId())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Server error!");
                alert.show();
                return false;
            }
        } else {
            question.upvote();
            if (!ServerCommunication.upvoteQuestion(question.getId())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Server error!");
                alert.show();
                return false;
            }
        }
        return true;
    }
}
