package nl.tudelft.oopp.demo.cellfactory;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nl.tudelft.oopp.demo.data.Question;

import java.util.Comparator;

public class StudentQuestionCell extends ListCell<Question> {

    private AnchorPane aP = new AnchorPane();
    private GridPane gP = new GridPane();
    private Question question;
    private ObservableList<Question> questions;
    private ObservableList<Question> answered;

    public StudentQuestionCell(ObservableList<Question> questions, ObservableList<Question> answered) {
        super();

        this.questions = questions;
        this.answered = answered;

        // Create visual cell
        createCell();
    }

    /**
     * Creates a cell with upvote and solved buttons, number of votes and the question.
     */
    private void createCell() {

        // Add grid pane to anchor pane
        aP.getChildren().add(gP);

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

        // Create buttons
        Button upVoteButton = new Button("Vote");
        Button markAnsweredButton = new Button("Mark as answered");
        Button deleteButton = new Button("Delete");
        Button editButton = new Button("Edit");

        // Align buttons
        markAnsweredButton.setAlignment(Pos.CENTER_RIGHT);

        // Wrapper for upvote button and votes
        HBox upVoteWrapper = new HBox(upVoteButton, upVotesLabel);
        upVoteWrapper.setAlignment(Pos.CENTER_LEFT);
        upVoteWrapper.setSpacing(5);

        // Wrapper for delete button and solved button
        HBox buttonWrapper = new HBox(markAnsweredButton, editButton, deleteButton);


        // Add elements to grid pane
        gP.add(ownerLabel, 0, 0);
        gP.add(questionLabel, 0,1);
        gP.add(upVoteWrapper, 0,2);
        gP.add(buttonWrapper, 1,2);

        // Give background colours
        gP.styleProperty().setValue("-fx-background-color: white");
        aP.styleProperty().setValue("-fx-background-color: #E5E5E5");

        // Align grid pane
        gP.setAlignment(Pos.CENTER);

        // Align grid pane in anchor pane
        AnchorPane.setTopAnchor(gP, 10.0);
        AnchorPane.setLeftAnchor(gP, 10.0);
        AnchorPane.setRightAnchor(gP, 10.0);
        AnchorPane.setBottomAnchor(gP, 10.0);

        //TODO possibly move to other files

        // Click event for upvote
        upVoteButton.setOnAction(event -> {

            if (this.question != null) {

                // Check if user already voted on question
                if (question.voted()) {
                    this.question.deUpvote();
                }
                else {
                    this.question.upvote();
                }

                // Sort questions again
                questions.sort(Comparator.comparing(Question::getUpvotes, Comparator.reverseOrder()));
            }

        });

        // Click event for solved
        markAnsweredButton.setOnAction(event -> {
            //TODO this button should only be visible for owners
            //TODO check if actual owner
            //TODO send to server

            answered.add(question);
            questions.remove(question);

        });


        // Click event for edit
        editButton.setOnAction(event -> {

            //TODO this button should only be visible for owners
            //TODO check if actual owner
            //TODO let user change the text
            //TODO send to server (PUT request)

        });


        // Click event for delete
        deleteButton.setOnAction(event -> {

            //TODO this button should only be visible for owners
            //TODO check if actual owner
            //TODO send to server

            // Remove question from list
            questions.remove(question);
        });

    }

    @Override
    protected void updateItem(Question item, boolean empty) {

        // Update listview
        super.updateItem(item, empty);

        // Empty list item
        if (empty || item == null) {
            setGraphic(null);
            setText("");
        }
        // Non-empty list item
        else {

            // Update question object
            this.question = item;

            // Look for number of votes and question
            Label upVotesLabel = (Label) gP.lookup("#upVotesLabel");
            Label questionLabel = (Label) gP.lookup("#questionLabel");
            Label ownerLabel = (Label) gP.lookup("#ownerLabel");

            // Update question and number of votes
            upVotesLabel.setText(String.valueOf(item.getUpvotes()));
            questionLabel.setText(item.getText());
            ownerLabel.setText(item.getOwner());

            //TODO add check for owner to make remove/solved buttons (in)visible

            //TODO add check for owner to make delete button (in)visible

            // Show graphic representation
            setGraphic(aP);
        }
    }

}
