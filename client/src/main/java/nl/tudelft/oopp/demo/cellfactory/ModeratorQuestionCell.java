package nl.tudelft.oopp.demo.cellfactory;

import java.net.URL;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.controllers.ModeratorRoomController;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.data.Question;

public class ModeratorQuestionCell extends ListCell<Question> {

    private AnchorPane anchorPane = new AnchorPane();
    private GridPane gridPane = new GridPane();
    private Question question;
    private ObservableList<Question> questions;
    private ObservableList<Question> answered;
    private TextField editableLabel;
    private boolean editing;
    private RoomController mrc;


    /**
     * Constructor for moderator question cell.
     * @param questions ObservableList of current questions
     * @param answered ObservableList of answered questions
     */
    public ModeratorQuestionCell(ObservableList<Question> questions,
                                 ObservableList<Question> answered, RoomController mrc) {

        super();

        this.questions = questions;
        this.answered = answered;
        this.editableLabel = new TextField();
        this.editing = false;
        this.mrc = mrc;

        // Create visual cell
        createCell();
    }

    /**
     * Creates a visual moderator cell for questions.
     */
    private void createCell() {

        // Add grid pane to anchor pane
        anchorPane.getChildren().add(gridPane);
        ColumnConstraints columnZeroConstraints = new ColumnConstraints();
        columnZeroConstraints.setPrefWidth(250);
        columnZeroConstraints.setPercentWidth(70);
        ColumnConstraints columnOneConstraints = new ColumnConstraints();
        columnOneConstraints.setPrefWidth(100);
        columnOneConstraints.setPercentWidth(30);
        gridPane.getColumnConstraints().add(columnZeroConstraints);
        gridPane.getColumnConstraints().add(columnOneConstraints);

        // Create all labels
        Label questionLabel = new Label();
        Label upVotesLabel = new Label();
        Label ownerLabel = new Label();

        // Assign ID's to labels
        questionLabel.setId("questionLabel");
        upVotesLabel.setId("upVotesLabel");
        ownerLabel.setId("ownerLabel");


        // Create buttons
        Button replyButton = new Button();
        replyButton.setId("replyButton");
        replyButton.setTooltip(new Tooltip("Answer question"));
        replyButton.setPrefWidth(26);
        URL path = StudentQuestionCell.class.getResource("/images/replyBlue.png");
        setButtonStyle(replyButton, path);
        replyButton.setCursor(Cursor.HAND);

        Button answerButton = new Button();
        answerButton.setId("answeredButton");
        answerButton.setTooltip(new Tooltip("Mark as answered"));
        answerButton.setPrefWidth(28);
        path = StudentQuestionCell.class.getResource("/images/checkmark.png");
        setButtonStyle(answerButton, path);
        answerButton.setCursor(Cursor.HAND);

        Button editButton = new Button();
        editButton.setId("editButton");
        editButton.setTooltip(new Tooltip("Edit Question"));
        editButton.setPrefWidth(25);
        path = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
        setButtonStyle(editButton, path);
        editButton.setCursor(Cursor.HAND);

        Button deleteButton = new Button();
        deleteButton.setId("deleteButton");
        deleteButton.setPrefWidth(28);
        path = StudentQuestionCell.class.getResource("/images/redTrash.png");
        setButtonStyle(deleteButton, path);
        deleteButton.setCursor(Cursor.HAND);

        // Create text area
        TextArea answerBox = new TextArea("");
        answerBox.setId("answerBox");
        answerBox.setWrapText(true);
        answerBox.setPrefHeight(125);
        answerBox.setPrefWidth(200);
        answerBox.setCursor(Cursor.TEXT);

        // Wrap edit and delete button
        HBox editDeleteWrapper = new HBox(editButton, deleteButton);
        editDeleteWrapper.setSpacing(5);

        // Wrap answer button and text area
        HBox answerWrapper = new HBox(answerBox, replyButton, answerButton);
        answerWrapper.setId("answerWrapper");
        answerWrapper.setSpacing(5);

        // Add elements to grid pane
        gridPane.add(ownerLabel, 0, 0);
        gridPane.add(questionLabel, 0,1);
        gridPane.add(answerWrapper, 0,2);
        gridPane.add(editDeleteWrapper, 1,2);
        gridPane.add(upVotesLabel, 1,0);

        // Give background colours
        gridPane.styleProperty().setValue("-fx-background-color: white");
        anchorPane.styleProperty().setValue("-fx-background-color: #E5E5E5");

        // Align grid pane
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Align grid pane in anchor pane
        AnchorPane.setTopAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, 10.0);
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setBottomAnchor(gridPane, 10.0);


        // Click event for the 'Edit' button
        editButton.setOnAction(event -> {

            if (this.question == null) {
                return;
            }

            // User saves changes
            if (editing) {

                // Send changes to server
                mrc.editQuestion(
                        this.question, editableLabel.getText());

                gridPane.getChildren().remove(editableLabel);
                gridPane.add(questionLabel, 0, 1);
                question.setText(editableLabel.getText());
                editButton.setTooltip(new Tooltip("Edit question"));
                editButton.setPrefWidth(25);
                URL url = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
                setButtonStyle(editButton, url);
                questionLabel.setText(editableLabel.getText());
                editing = false;

            } else { // User wants to make changes

                gridPane.getChildren().remove(questionLabel);
                gridPane.add(editableLabel, 0,1);
                editButton.setTooltip(new Tooltip("Save Changes"));
                editButton.setPrefWidth(27);
                URL url = StudentQuestionCell.class.getResource("/images/checkGreen.png");
                setButtonStyle(editButton, url);
                editableLabel.setText(question.getText());
                editing = true;

            }
        });

        // Click event for the 'Mark answered' button
        answerButton.setOnAction(event -> {

            // Next line marks the question as answered in the DB
            ServerCommunication.markQuestionAsAnswered(question.getId());

            // The if is to submit the already written text before marking
            if (!answerBox.getText().equals("")) {
                ((ModeratorRoomController) mrc).setAnswer(this.question, answerBox.getText());
            }

            // Next 2 lines are to make the change to look instant
            questions.remove(question);
            answered.add(question);
        });

        // Click event for the 'Reply' button
        replyButton.setOnAction(event -> {

            // Send answer to server to store in db
            ((ModeratorRoomController) mrc).setAnswer(this.question, answerBox.getText());

            question.setAnswer(answerBox.getText());   // Those will probably get removed later
            answerBox.setPromptText(answerBox.getText());
            answerBox.clear();
            answerBox.deselect();
        });

        // Click event for the 'Delete' button
        deleteButton.setOnAction(event -> {

            // Send to server to delete from DB
            mrc.deleteQuestion(this.question);

            // Remove question from list
            questions.remove(question);
        });
    }

    /**
     * Updates the item in the ListView.
     * @param item updated item
     * @param empty true if empty, false if not
     */
    @Override
    protected void updateItem(Question item, boolean empty) {

        // Update listview
        super.updateItem(item, empty);

        // Empty list item
        if (empty || item == null) {

            setGraphic(null);
            setText("");

        } else { // Non-empty list item

            // Update question object
            this.question = item;

            // Look for question and owner label
            Label questionLabel = (Label) gridPane.lookup("#questionLabel");
            Label ownerLabel = (Label) gridPane.lookup("#ownerLabel");
            Label upVotesLabel = (Label) gridPane.lookup("#upVotesLabel");

            // Update question
            if (questionLabel != null) {
                questionLabel.setText(item.getText());
            }

            ownerLabel.setText(item.getOwner());
            upVotesLabel.setText(item.getUpvotes() + " Votes");

            // Show graphic representation
            setGraphic(anchorPane);

            // Next 2 lines are for showing the current answer to the question as prompt
            TextArea answerBox = (TextArea) gridPane.lookup("#answerBox");
            answerBox.setPromptText(question.getAnswer());

            Button editButton = (Button) gridPane.lookup("#editButton");
            Button deleteButton = (Button) gridPane.lookup("#deleteButton");
            HBox answerWrapper = (HBox) gridPane.lookup("#answerWrapper");

            // TODO: modify when 2nd answer button added (Senne)
            // TODO: Create zen cell?
            ModeratorRoomController mrcCast = (ModeratorRoomController) mrc;
            // if zen mode is active
            if (mrcCast.getZenMode()) {
                answerWrapper.setVisible(false);
                editButton.setVisible(false);
                deleteButton.setVisible(false);
            } else {
                answerWrapper.setVisible(true);
                editButton.setVisible(true);
                deleteButton.setVisible(true);
            }
        }
    }

    private void setButtonStyle(Button button, URL path) {
        button.setStyle("-fx-background-image: url('" + path + "');"
                + " -fx-background-repeat: no-repeat;"
                + " -fx-background-size: 100% 100%;");
    }
}
