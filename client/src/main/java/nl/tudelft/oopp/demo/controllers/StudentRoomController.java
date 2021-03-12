package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;

public class StudentRoomController {

    @FXML
    private Button submit;

    @FXML
    private TextArea question;

    @FXML
    private AnchorPane anchor;

    private String name;
    private Room room;

    /**
     * Used in SplashController to pass the username and the room object.
     *
     * @param name the name entered by the user in splash
     * @param room the room corresponding to the code entered
     */
    public void setData(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    /**
     * The method is executed when the submit question button is pressed.
     * If the room is not active - the student sees an alert of type warning.
     * If the room is active but the question form is blank - ..
     * .. they see an alert of type error.
     * Else the question is sent to the server via a POST request.
     */
    public void submitQuestion() {
        if (this.room.isActive()) {
            if (question.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You need to enter a question to submit it!");
                alert.show();
            } else {
                // TODO: Add question to DB and display it to all users

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The lecture is over. You cannot ask questions anymore!");
            alert.show();
            question.setDisable(true);
            submit.setDisable(true);
        }
    }

    /**
     * Still a test class. The idea is that the students ..
     * .. are alerted that the room has been closed.
     */
    public void test() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The lecture has ended!");
        alert.show();
    }


    /**
     * Deletes this question upon pressing "delete" or "mark as answered" buttons.
     *
     * @param questionToRemove - Question to be removed from database.
     */
    public static boolean deleteQuestion(Question questionToRemove) {

        // TODO check if question id on client side corresponds to server side id?!

        if (questionToRemove == null || !ServerCommunication.deleteQuestion(questionToRemove.getId())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Invalid operation!");
            alert.show();
            return false;
        }
        return true;
    }


    /**
     * Edits this question according to new text entered upon pressing "edit" button.
     *
     * @param questionToEdit - Question to edit content of in database.
     */
    public static boolean editQuestion(Question questionToEdit, String update) {

        if (questionToEdit != null && update.length() > 0) {

            questionToEdit.setText(update);

            if (!ServerCommunication.editQuestion(questionToEdit.getId(), update)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Invalid operation!");
                alert.show();
                return false;
            }
            return true;
        }
        return false;
    }


        /** Increments the number of upvotes of this question by 1.
         * @param question - Question to upvote
         */
        public static void upvoteQuestion (Question question){

            if (question != null) {
                // Check if user already voted on question
                if (question.voted()) {
                    question.deUpvote();
                } else {
                    question.upvote();
                }
            }
        }


    }
