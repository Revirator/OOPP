package nl.tudelft.oopp.demo.controllers;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.StudentView;

public class StudentRoomController extends RoomController {
    @FXML
    private AnchorPane anchor;

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
    private Label lectureName;

    private StudentView studentView;

    private boolean questionAllowed;

    /** Used in SplashController to pass the user and the room object.
     * Data injected by start() in StudentView.
     * @param user the student that is using the window
     * @param room the room corresponding to the code entered
     * @param studentView - corresponding view to this controller (to add questions)
     */
    public void setData(User user, Room room, StudentView studentView) {
        super.setData(user, room, studentView);
        this.studentView = studentView;
        this.lectureName.setText(room.getRoomName());
        this.questionAllowed = true;

        // creates a service that allows a method to be called every timeframe
        ScheduledService<Boolean> serviceAllow = new ScheduledService<>() {
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

        // every 20 seconds, questionAllowed is set on true again
        serviceAllow.setPeriod(Duration.seconds(20));
        serviceAllow.setOnRunning(e -> questionAllowed = true);
        serviceAllow.start();
    }

    /** Updates the room object and the user by calling the getRoom() ..
     * .. and getStudent() methods in ServerCommunication.
     */
    @Override
    public void roomRefresher() {
        Room room = super.getRoom();
        User student = super.getUser();
        Room newRoom = ServerCommunication.getRoom(room.getStudentsLink(), false);
        if (room.isActive() && !newRoom.isActive()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("The lecture has ended! You cannot ask questions or "
                    + "provide\nfeedback anymore!");
            alert.show();
        }
        super.setRoom(newRoom);
        // The server returns a student with the room field being null
        Student tempStudent = (Student) ServerCommunication.getStudent(student.getId());
        if (!((Student) student).isBanned() && tempStudent.isBanned()) {
            super.setUser(new Student(tempStudent.getId(), tempStudent.getNickname(), room,
                    tempStudent.getIpAddress(), tempStudent.isBanned()));
            resetFeedback();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("It seems like you got banned!"
                    + "\nThe window will now close for you!");
            alert.showAndWait();
            Stage stage = (Stage) anchor.getScene().getWindow();
            stage.close();
        } else {
            student = new Student(tempStudent.getId(), tempStudent.getNickname(), room,
                    tempStudent.getIpAddress(), tempStudent.isBanned());
            this.studentView.setData(student,room);
        }
    }

    /** Callback method for "Submit" button in student room.
     * If the room is not active - the student sees an alert of type warning.
     * If the room is active but the question form is blank - ..
     * .. they see an alert of type error.
     * Else the question is sent to the server via a POST request.
     */
    public void submitQuestion() {
        Room room = super.getRoom();
        User student = super.getUser();
        if (room.isActive()) {
            if (questionBox.getText().length() < 7) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter at least 8 characters!");
                alert.show();
            } else if (!questionAllowed) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Please wait for a total of 20 seconds before"
                        + "\nsubmitting another question");
                alert.show();
            } else if (questionBox.getText().contains("&")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The symbol '&' cannot be used.");
                alert.show();
            } else {
                // Create new question, id returned by server (needed for delete/edit).
                Question newQuestion = new Question(room.getRoomId(), questionBox.getText(),
                        student.getNickname(), true);
                Long newId = ServerCommunication.postQuestion(newQuestion);
                newQuestion.setId(newId);
                questionBox.clear();
                this.studentView.addQuestion(newQuestion);
                questionAllowed = false;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The lecture is over. You cannot ask questions anymore!");
            alert.show();
            questionBox.clear();
            questionBox.setDisable(true);
            submit.setDisable(true);
        }
    }


    /** Increments the peopleThinkingLectureIsTooSlow field in Room ..
     * .. both on the server and client side by one.
     */
    public void lectureTooSlow() {
        Room room = super.getRoom();
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

        Room room = super.getRoom();

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
        if (resetButton.isDisabled()) {
            return;
        }
        Room room = super.getRoom();
        if (!room.isActive()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("The lecture is over! You cannot send feedback anymore!");
            alert.show();
            tooFastButton.setVisible(true);
            tooSlowButton.setVisible(true);
            tooFastButton.setDisable(true);
            tooSlowButton.setDisable(true);
            resetButton.setDisable(true);
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
}
