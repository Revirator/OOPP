package nl.tudelft.oopp.demo.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.data.Room;

public class LinkRoomController {

    @FXML
    private TextField studentCode;

    @FXML
    private TextField moderatorCode;

    @FXML
    private ImageView copiedStudent;

    @FXML
    private ImageView copiedModerator;

    private Room room;

    final Clipboard clipboard = Clipboard.getSystemClipboard();

    public void setData(Room room) {
        this.room = room;
    }

    /** Copies the student room code to the clipboard.
     */
    public void copyStudentsCode() {
        final ClipboardContent content = new ClipboardContent();
        content.putString(studentCode.getText());
        this.clipboard.setContent(content);
        copiedStudent.setVisible(true);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), e -> copiedStudent.setVisible(false)));
        timeline.play();
    }

    /** Copies the moderator room code to the clipboard.
     */
    public void copyModeratorsCode() {
        final ClipboardContent content = new ClipboardContent();
        content.putString(moderatorCode.getText());
        this.clipboard.setContent(content);
        copiedModerator.setVisible(true);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), e -> copiedModerator.setVisible(false)));
        timeline.play();
    }

    public void main(String[] args) {
        studentCode.setText(this.room.getStudentsLink());
        moderatorCode.setText(this.room.getModeratorLink());
    }

    /**
     * Getter for room
     * (Used for testing).
     * @return
     */
    public Room getRoom() {
        return this.room;
    }
}
