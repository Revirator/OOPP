package nl.tudelft.oopp.demo.cellfactory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import nl.tudelft.oopp.demo.data.Question;

public class StudentQuestionCell extends ListCell<Question> {

    private AnchorPane aP = new AnchorPane();
    private GridPane gP = new GridPane();
    private Question question;
    private boolean voted;

    public StudentQuestionCell() {
        super();

        voted = false;

        // Create visual cell
        createCell();
    }

    /**
     * Creates a cell with upvote and solved buttons, number of votes and the question.
     */
    private void createCell() {

//        AnchorPane aP = new AnchorPane();

        Label questionLabel = new Label();
        Label upVotesLabel = new Label();
        upVotesLabel.setId("upVotesLabel");

        questionLabel.wrapTextProperty().set(true);
        questionLabel.maxWidth(Double.MAX_VALUE);
        questionLabel.setId("questionLabel");

        questionLabel.setAlignment(Pos.CENTER_LEFT);

        Button upVoteButton = new Button("Vote");
        Button markAnsweredButton = new Button("Mark as answered");
        markAnsweredButton.setAlignment(Pos.CENTER_RIGHT);

        markAnsweredButton.wrapTextProperty().set(true);
        upVoteButton.wrapTextProperty().set(true);

        HBox upVoteWrapper = new HBox(upVoteButton, upVotesLabel);
        upVoteWrapper.setAlignment(Pos.CENTER_LEFT);
        upVoteWrapper.setSpacing(5);

        gP.add(questionLabel, 0,0);
        gP.add(upVoteWrapper, 0,1);
        gP.add(markAnsweredButton, 1,1);

//        gP.gridLinesVisibleProperty().set(true);

        gP.styleProperty().setValue("-fx-background-color: white");

        gP.setAlignment(Pos.CENTER);

//        aP.setPadding(new Insets(10,10,10,10));

//        aP.getChildren().clear();
        aP.getChildren().add(gP);
//        aP.maxWidth(Double.MAX_VALUE);
        gP.maxWidthProperty().set(Double.MAX_VALUE);
        gP.maxWidth(Double.MAX_VALUE);

        for (ColumnConstraints c : gP.getColumnConstraints()) {
            c.setMaxWidth(Double.MAX_VALUE);
            c.maxWidthProperty().set(Double.MAX_VALUE);
            c.setHgrow(Priority.ALWAYS);
        }

        AnchorPane.setTopAnchor(gP, 10.0);
        AnchorPane.setLeftAnchor(gP, 10.0);
        AnchorPane.setRightAnchor(gP, 10.0);
        AnchorPane.setBottomAnchor(gP, 10.0);

        aP.styleProperty().setValue("-fx-background-color: #E5E5E5");

        setGraphic(aP);

        // Click event for upvote
        upVoteButton.setOnAction(event -> {

            if (this.question != null) {

                if (voted) {
                    this.question.deUpvote();
                }
                else {
                    this.question.upvote();
                }

                voted = !voted;
                updateItem(question, false);
            }

        });

        // Click event for solved
        markAnsweredButton.setOnAction(event -> {

            //TODO implement marked as solved

        });

    }

    @Override
    protected void updateItem(Question item, boolean empty) {
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

            // Update question and number of votes
            upVotesLabel.setText(String.valueOf(question.getUpvotes()));
            questionLabel.setText(item.getText());

            // Show graphic representation
            setGraphic(aP);
        }
    }

}
