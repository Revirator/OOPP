package nl.tudelft.oopp.demo.cellfactory;

import java.net.URL;
import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.data.Question;

public class StudentQuestionCell extends ListCell<Question> {

    private AnchorPane anchorPane = new AnchorPane();
    private GridPane gridPane = new GridPane();
    private Question question;
    private ObservableList<Question> questions;
    private ObservableList<Question> answered;
    private boolean editing;
    private TextField editableLabel;
    private RoomController src;

    /** Initialized for each question by StudentView.
     * Constructor for student question cell.
     * @param questions ObservableList of the current questions
     * @param answered ObservableList of all answered questions
     */
    public StudentQuestionCell(ObservableList<Question> questions,
                               ObservableList<Question> answered, RoomController src) {

        super();

        this.questions = questions;
        this.answered = answered;
        this.editing = false;
        this.editableLabel = new TextField();
        this.src = src;

        // Create visual cell
        createOwnerCell();
    }



    /**
     * Creates a cell with upvote and solved buttons, number of votes and the question.
     */
    private void createOwnerCell() {

        // Add grid pane to anchor pane
        anchorPane.getChildren().add(gridPane);

        // Create all labels and assign ids
        Label questionLabel = new Label();
        questionLabel.setId("questionLabel");

        Label upVotesLabel = new Label();
        upVotesLabel.setId("upVotesLabel");

        Label ownerLabel = new Label();
        ownerLabel.setId("ownerLabel");

        Label answerLabel = new Label("Answer: ");
        answerLabel.setId("answerLabel");

        // Position labels
        answerLabel.setAlignment(Pos.CENTER_LEFT);
        questionLabel.setAlignment(Pos.CENTER_LEFT);
        ownerLabel.setAlignment(Pos.CENTER_LEFT);


        // Create buttons in wrappers
        Button upVoteButton = new Button();
        upVoteButton.setId("UpvoteButton");
        upVoteButton.setPrefWidth(28);
        URL path = StudentQuestionCell.class.getResource("/images/likeBlue.png");
        setButtonStyle(upVoteButton, path);
        upVoteButton.setCursor(Cursor.HAND);
        HBox upVoteWrapper = new HBox(upVoteButton, upVotesLabel);
        upVoteWrapper.setAlignment(Pos.CENTER_LEFT);
        upVoteWrapper.setSpacing(5);

        Button markAnsweredButton = new Button();
        markAnsweredButton.setPrefWidth(28);
        path = StudentQuestionCell.class.getResource("/images/checkmark.png");
        setButtonStyle(markAnsweredButton, path);
        markAnsweredButton.setCursor(Cursor.HAND);

        Button deleteButton = new Button();
        deleteButton.setPrefWidth(28);
        path = StudentQuestionCell.class.getResource("/images/redTrash.png");
        setButtonStyle(deleteButton, path);
        deleteButton.setCursor(Cursor.HAND);
        HBox buttonWrapper = new HBox(markAnsweredButton, deleteButton);
        buttonWrapper.setId("AnsweredOrDelete");

        // Align buttons
        markAnsweredButton.setAlignment(Pos.CENTER_RIGHT);

        Button editQuestionButton = new Button();
        editQuestionButton.setId("EditButton");
        editQuestionButton.setPrefWidth(25);
        path = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
        setButtonStyle(editQuestionButton, path);
        editQuestionButton.setCursor(Cursor.HAND);
        HBox questionWrapper = new HBox(questionLabel, editQuestionButton);

        // Add elements to grid pane
        gridPane.add(buttonWrapper, 1,3);
        gridPane.add(upVoteWrapper, 0,3);
        gridPane.add(ownerLabel, 0, 0);
        gridPane.add(questionWrapper, 0,1);
        gridPane.add(answerLabel, 0, 2);

        // Give background colours
        gridPane.styleProperty().setValue("-fx-background-color: white");
        anchorPane.styleProperty().setValue("-fx-background-color: #E5E5E5");
        // gP.setGridLinesVisible(true);


        // Align grid pane
        gridPane.setAlignment(Pos.CENTER);

        // Align grid pane in anchor pane
        AnchorPane.setTopAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, 10.0);
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setBottomAnchor(gridPane, 10.0);


        // Click event for upvote
        upVoteButton.setOnAction(event -> {

            src.upvoteQuestion(this.question);
            //   // Not needed anymore now we sort by time
            //            questions.sort(Comparator.comparing(Question::getUpvotes,
            //                        Comparator.reverseOrder()));
        });


        // Click event for solved
        markAnsweredButton.setOnAction(event -> {

            if (this.question.isOwner()) {
                // Next line marks the question as answered in the DB
                ServerCommunication.markQuestionAsAnswered(question.getId());
                answered.add(question);     // Those will probably get removed later
                questions.remove(question); // since they change stuff only locally
            }
        });


        // Click event for delete
        deleteButton.setOnAction(event -> {

            if (this.question.isOwner()) {
                src.deleteQuestion(this.question);
                questions.remove(question);
            }
        });


        // Click event for editing
        editQuestionButton.setOnAction(event -> {

            questionWrapper.getChildren().clear();

            // User presses "save changes"
            if (editing) {

                src.editQuestion(
                        this.question, editableLabel.getText());
                questionWrapper.getChildren().addAll(questionLabel, editQuestionButton);
                editQuestionButton.setPrefWidth(25);
                URL url = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
                setButtonStyle(editQuestionButton, url);
                questionLabel.setText(editableLabel.getText());
                editing = false;

            } else { // User presses "edit"
                questionWrapper.getChildren().addAll(editableLabel, editQuestionButton);
                editQuestionButton.setPrefWidth(27);
                URL url = StudentQuestionCell.class.getResource("/images/checkGreen.png");
                setButtonStyle(editQuestionButton, url);
                editableLabel.setText(question.getText());
                editing = true;
            }
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

            // Look for number of votes and question
            Label upVotesLabel = (Label) gridPane.lookup("#upVotesLabel");
            Label questionLabel = (Label) gridPane.lookup("#questionLabel");
            Label ownerLabel = (Label) gridPane.lookup("#ownerLabel");

            // Update question and number of votes
            upVotesLabel.setText(String.valueOf(item.getUpvotes()));

            // don't update while editing (NullPointerException)
            if (questionLabel != null) {
                questionLabel.setText(item.getText());
            }

            ownerLabel.setText(item.getOwner());

            Label answerLabel = (Label) gridPane.lookup("#answerLabel");
            answerLabel.setText("Answer: " + question.getAnswer());

            HBox answeredOrDelete = (HBox) gridPane.lookup("#AnsweredOrDelete");
            Button editButton = (Button) gridPane.lookup("#EditButton");
            editButton.setCursor(Cursor.HAND);
            // Button upvoteButton = (Button) gridPane.lookup("#UpvoteButton");
            if (!this.question.isOwner()) {
                answeredOrDelete.setVisible(false);
                editButton.setVisible(false);
                // upvoteButton.setVisible(false);
            } else {
                answeredOrDelete.setVisible(true);
                editButton.setVisible(true);
            }

            // Show graphic representation
            setGraphic(anchorPane);
        }
    }

    private void setButtonStyle(Button button, URL path) {
        button.setStyle("-fx-background-image: url('" + path + "');"
                + " -fx-background-repeat: no-repeat;"
                + " -fx-background-size: 100% 100%;");
    }
}
