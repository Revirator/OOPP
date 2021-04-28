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

    /**
     * Copies the student room code to the clipboard.
     */
    public void copyStudentsCode() {
        copyCode(studentCode.getText(), copiedStudent);
    }

    /**
     * Copies the moderator room code to the clipboard.
     */
    public void copyModeratorsCode() {
        copyCode(moderatorCode.getText(), copiedModerator);
    }

    /**
     * Copies a given code to the clipboard.
     * @param code code to copy
     * @param imageView imageview to disable
     */
    public void copyCode(String code, ImageView imageView) {
        final ClipboardContent content = new ClipboardContent();
        content.putString(code);
        this.clipboard.setContent(content);
        imageView.setVisible(true);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), e -> imageView.setVisible(false)));
        timeline.play();
    }

    public void main(String[] args) {
        studentCode.setText(this.room.getStudentsLink());
        moderatorCode.setText(this.room.getModeratorLink());
    }

    /**
     * Getter for room
     * (Used for testing).
     * @return the Room object
     */
    public Room getRoom() {
        return this.room;
    }
}
