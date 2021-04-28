package nl.tudelft.oopp.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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

    private boolean zenModeActive;

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
        zenModeActive = false;
        this.lectureName.setText(room.getRoomName());
        setFeedback();
    }

    /**
     * Refreshes the current room.
     */
    public void roomRefresher() {
        super.setRoom(ServerCommunication.getRoom(
               super.getRoom().getStudentsLink(), false));
        this.moderatorView.setData(super.getUser(), super.getRoom());
        setFeedback();
    }

    /**
     * Updates the feedback for the moderators.
     * For it to be done in real time it needs the fetch request.
     */
    public void setFeedback() {
        Room room = super.getRoom();
        if (room.getStudents().size() > 0) {
            tooSlowLabel.setText(room.getPeopleThinkingLectureIsTooSlow() * 100
                    / room.getStudents().size() + "%");
            if (Integer.parseInt(tooSlowLabel.getText().replace("%", "")) < 10) {
                tooSlowLabel.setTextFill(Paint.valueOf("DARKGREEN"));
            } else {
                tooSlowLabel.setTextFill(Paint.valueOf("RED"));
            }
            tooFastLabel.setText(room.getPeopleThinkingLectureIsTooFast() * 100
                    / room.getStudents().size() + "%");
            if (Integer.parseInt(tooFastLabel.getText().replace("%", "")) < 10) {
                tooFastLabel.setTextFill(Paint.valueOf("DARKGREEN"));
            } else {
                tooFastLabel.setTextFill(Paint.valueOf("RED"));
            }
        } else {
            tooSlowLabel.setText(0 + "%");
            tooSlowLabel.setTextFill(Paint.valueOf("DARKGREEN"));
            tooFastLabel.setText(0 + "%");
            tooFastLabel.setTextFill(Paint.valueOf("DARKGREEN"));
        }
    }

    /**
     * The method that is executed when the End lecture button is clicked.
     * Updates the status of the room to inactive so that new questions..
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
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser
                    .ExtensionFilter("Text Files (*.txt,*.md)", "*.txt", "*.md"));
            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile == null) {
                return;
            }
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(selectedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (printWriter == null) {
                return;
            }
            List<Question> answeredQuestions = ServerCommunication
                    .getAnsweredQuestions(room.getRoomId());
            for (Question answeredQuestion : answeredQuestions) {
                printWriter.println(answeredQuestion.toString());
            }
            printWriter.close();
        }
    }

    /**
     * Callback method for the "Zen Mode" button in Moderator Room.
     * Update in ModeratorQuestionCell makes the following buttons invisible: ..
     *  .. "edit", "answer", "mark answered", "delete"
     *  .. as well as answer text box
     */
    public void zenMode() {
        // zen mode becomes active
        if (!zenModeActive) {
            zenModeActive = true;
            moderatorView.bindZenCellFactory();
        } else {
            zenModeActive = false;
            moderatorView.bindCellFactory(this);
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

    /**
     * Opens another window that display the room codes ..
     * .. for students and moderators so that they can be copied.
     */
    public void showLinks() {
        URL xmlUrl = getClass().getResource("/windowWithLinks.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(xmlUrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Something went wrong! Could not load the links");
            error.show();
        }
        Stage newStage = new Stage();
        assert root != null;
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchor");
        anchorPane.requestFocus();
        newStage.getIcons().add(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        newStage.show();
        LinkRoomController linkRoomController = loader.getController();
        linkRoomController.setData(super.getRoom());
        linkRoomController.main(new String[0]);
    }

    /**
     * zenModeActive getter
     * (Used for testing).
     */
    public boolean isZenModeActive() {
        return this.zenModeActive;
    }
}
