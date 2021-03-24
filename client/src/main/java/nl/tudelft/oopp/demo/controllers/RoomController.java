package nl.tudelft.oopp.demo.controllers;

import java.util.List;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.AppView;

public abstract class RoomController {

    private User user;
    private Room room;
    private AppView appView;

    /**
     * Used to pass the user and the room object.
     * This method should be called after the fetch request so that it updates the information.
     * @param user - the user that is using the window
     * @param room - the room corresponding to the code entered
     * @param appView - corresponding view to this controller (to add questions)
     */
    public void setData(User user, Room room, AppView appView) {

        this.user = user;
        this.room = room;
        this.appView = appView;

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
     * Getter for the current room.
     * @return current room
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Setter for the room.
     * @param room current room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Getter for the current user.
     * @return current user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Setter for the current user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Calls methods in ServerCommunication to get updated lists from the database.
     * Updates the actual view.
     */
    public void questionRefresher() {
        List<Question> questionList = ServerCommunication.getQuestions(room.getRoomId());
        List<Question> answeredList = ServerCommunication.getAnsweredQuestions(room.getRoomId());
        appView.update(questionList, answeredList);
    }

    /**
     * Calls methods in ServerCommunication to get updated lists from the database.
     * Updates the user views (periodically called by refresher)
     */
    public void participantRefresher() {
        List<Student> studentList = ServerCommunication.getStudents(room.getRoomId());
        List<Moderator> moderatorList = ServerCommunication.getModerators(room.getRoomId());
        appView.updateParticipants(studentList, moderatorList);

    }

    /** Updates the room object and the feedback by calling the getRoom() ..
     * .. method in ServerCommunication.
     */
    public abstract void roomRefresher();

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
     * Deletes this question upon pressing "delete" button.
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
     * Upvote a question server-side.
     * @param question question to upvote
     * @return true if successful, false if not
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
