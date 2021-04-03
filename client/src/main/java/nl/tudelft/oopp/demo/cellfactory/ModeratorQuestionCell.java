package nl.tudelft.oopp.demo.cellfactory;

import java.net.URL;
import java.sql.Time;

import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.communication.PutServerCommunication;
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
    private boolean startTyping;    // used for the 'Someone is answering...'


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
        this.startTyping = false;
        // Create visual cell
        createCell();
    }

    /**
     * Creates a visual moderator cell for questions.
     */
    private void createCell() {

        // Add grid pane to anchor pane
        anchorPane.getChildren().add(gridPane);

        // Create all labels
        Label questionLabel = new Label();
        Label upVotesLabel = new Label();
        Label ownerLabel = new Label();

        // Assign ID's to labels
        questionLabel.setId("questionLabel");
        upVotesLabel.setId("upVotesLabel");
        ownerLabel.setId("ownerLabel");

        // Position labels
        questionLabel.setAlignment(Pos.CENTER_LEFT);
        ownerLabel.setAlignment(Pos.CENTER_LEFT);
        upVotesLabel.setAlignment(Pos.CENTER_RIGHT);

        // Create buttons

        // 'Reply' button
        Button replyButton = new Button();
        replyButton.setId("replyButton");
        replyButton.setPrefWidth(26);
        URL path = StudentQuestionCell.class.getResource("/images/replyBlue.png");
        setButtonStyle(replyButton, path);
        replyButton.setCursor(Cursor.HAND);

        Button answerButton = new Button();
        answerButton.setId("answeredButton");
        answerButton.setPrefWidth(28);
        path = StudentQuestionCell.class.getResource("/images/checkmark.png");
        setButtonStyle(answerButton, path);
        answerButton.setCursor(Cursor.HAND);

        Button editButton = new Button();
        editButton.setId("editButton");
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
        answerBox.setCursor(Cursor.TEXT);

        // Wrap edit and delete button
        HBox editDeleteWrapper = new HBox(editButton, deleteButton);
        editDeleteWrapper.setSpacing(5);

        // Wrap answer button and text area
        HBox answerWrapper = new HBox(answerBox, answerButton, replyButton);
        answerWrapper.setId("answerWrapper");
        answerWrapper.setSpacing(5);

        // Align buttons
        answerButton.setAlignment(Pos.CENTER_LEFT);
        deleteButton.setAlignment(Pos.CENTER_RIGHT);
        editButton.setAlignment(Pos.CENTER_RIGHT);

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

        // creates a service that will be used to know when
        // to mark a question as not being answered
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

        service.setPeriod(Duration.seconds(1));

        // When the service is done it sends a request
        // to mark question as no longer being answered
        service.setOnSucceeded(e -> {
            PutServerCommunication.markQuestionAsIsNotBeingAnswered(question.getId());
            startTyping = false;
        });

        // Trigger event for every time something is entered in the answerBox
        answerBox.setOnKeyTyped(event -> {
            if (startTyping == false) {
                PutServerCommunication.markQuestionAsIsBeingAnswered(question.getId());
                startTyping = true;
                service.restart();
            }

            // 2 seconds delay after the person has stopped writing
            service.setDelay(Duration.seconds(2));
        });


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
                editButton.setPrefWidth(25);
                URL url = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
                setButtonStyle(editButton, url);
                questionLabel.setText(editableLabel.getText());
                editing = false;

            } else { // User wants to make changes

                gridPane.getChildren().remove(questionLabel);
                gridPane.add(editableLabel, 0,1);
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
                String answer = answerBox.getText() + " -" + mrc.getUser().getNickname();
                ((ModeratorRoomController) mrc).setAnswer(this.question, answer);
            }

            // Next 2 lines are to make the change to look instant
            questions.remove(question);
            answered.add(question);
        });

        // Click event for the 'Reply' button
        replyButton.setOnAction(event -> {
            String answer = answerBox.getText() + " -" + mrc.getUser().getNickname();

            // Send answer to server to store in db
            ((ModeratorRoomController) mrc).setAnswer(this.question, answer);

            question.setAnswer(answer);   // Those will probably get removed later
            answerBox.setPromptText(answer);
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

            // Next few lines are for showing the current answer to the question as prompt
            // or showing 'Someone is answering...' in case some is... answering
            TextArea answerBox = (TextArea) gridPane.lookup("#answerBox");

            if (item.isBeingAnswered()) {
                answerBox.setPromptText("Someone is answering...");
            } else {
                answerBox.setPromptText(this.question.getAnswer());
            }

            // Show graphic representation
            setGraphic(anchorPane);

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
