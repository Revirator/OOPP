package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;

public abstract class RoomController {

    public abstract boolean editQuestion(Question questionToEdit, String update);

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

    /**
     * Delete a question server-side.
     * @param questionToRemove question to remove
     * @return true if successful, false if not
     */
    public abstract boolean deleteQuestion(Question questionToRemove);

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
