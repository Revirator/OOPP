package nl.tudelft.oopp.demo.cellfactory;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.data.Question;

public class ModeratorAnsweredCell extends ListCell<Question> {

    private AnchorPane aP = new AnchorPane();
    private GridPane gP = new GridPane();
    private Question question;
    private ObservableList<Question> answered;

    public ModeratorAnsweredCell(ObservableList<Question> answered) {
        super();

        this.answered = answered;

        // Create visual cell
        createCell();
    }

    /**
     * Creates a cell for answered questions.
     */
    private void createCell() {

        // Add grid pane to anchor pane
        aP.getChildren().add(gP);

        // Create all labels
        Label questionLabel = new Label();
        Label upVotesLabel = new Label();
        Label ownerLabel = new Label();
        Label answerLabel = new Label();

        // Assign ID's to labels
        questionLabel.setId("questionLabel");
        upVotesLabel.setId("upVotesLabel");
        ownerLabel.setId("ownerLabel");
        answerLabel.setId("answerLabel");

        // Position labels
        questionLabel.setAlignment(Pos.CENTER_LEFT);
        ownerLabel.setAlignment(Pos.CENTER_LEFT);
        upVotesLabel.setAlignment(Pos.CENTER_RIGHT);
        answerLabel.setAlignment(Pos.CENTER_LEFT);

        // Add elements to grid pane
        gP.add(ownerLabel, 0, 0);
        gP.add(questionLabel, 0,1);
        gP.add(upVotesLabel, 1,1);
        gP.add(answerLabel, 0,2);

        // Give background colours
        gP.styleProperty().setValue("-fx-background-color: white");
        aP.styleProperty().setValue("-fx-background-color: #E5E5E5");

        // Align grid pane
        gP.setAlignment(Pos.CENTER);

        // Set gaps between rows and columns
        gP.setVgap(5);
        gP.setHgap(5);

        // Align grid pane in anchor pane
        AnchorPane.setTopAnchor(gP, 10.0);
        AnchorPane.setLeftAnchor(gP, 10.0);
        AnchorPane.setRightAnchor(gP, 10.0);
        AnchorPane.setBottomAnchor(gP, 10.0);

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
            Label answerLabel = (Label) gP.lookup("#answerLabel");

            // Update question and number of votes
            upVotesLabel.setText(item.getUpvotes() + " votes");
            questionLabel.setText(item.getText());
            ownerLabel.setText(item.getOwner());
            answerLabel.setText("Answer: " + item.getAnswer());

            // Show graphic representation
            setGraphic(aP);
        }
    }
}
