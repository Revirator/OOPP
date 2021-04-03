package nl.tudelft.oopp.demo.cellfactory;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.data.Question;

public class ZenAnsweredCell extends ListCell<Question> {

    private AnchorPane anchorPane = new AnchorPane();
    private GridPane gridPane = new GridPane();

    /**
     * Constructor for moderator answer cell.
     */
    public ZenAnsweredCell() {
        super();

        // Create visual cell
        createCell();
    }

    /**
     * Creates a cell for answered questions.
     */
    private void createCell() {

        // Add grid pane to anchor pane
        anchorPane.getChildren().add(gridPane);

        // Create all labels with ID
        Label questionLabel = new Label();
        questionLabel.setId("questionLabel");

        Label upVotesLabel = new Label();
        upVotesLabel.setId("upVotesLabel");

        Label ownerLabel = new Label();
        ownerLabel.setId("ownerLabel");

        Label answerLabel = new Label();
        answerLabel.setId("answerLabel");

        // Position labels
        questionLabel.setAlignment(Pos.CENTER_LEFT);
        ownerLabel.setAlignment(Pos.CENTER_LEFT);
        upVotesLabel.setAlignment(Pos.CENTER_RIGHT);
        answerLabel.setAlignment(Pos.CENTER_LEFT);

        // Add elements to grid pane
        gridPane.add(ownerLabel, 0, 0);
        gridPane.add(questionLabel, 0,1);
        gridPane.add(upVotesLabel, 1,0);
        gridPane.add(answerLabel, 0,2);

        // Give background colours
        gridPane.styleProperty().setValue("-fx-background-color: white");
        anchorPane.styleProperty().setValue("-fx-background-color: #E5E5E5");

        // Align grid pane
        gridPane.setAlignment(Pos.CENTER);

        // Set gaps between rows and columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Align grid pane in anchor pane
        AnchorPane.setTopAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, 10.0);
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setBottomAnchor(gridPane, 10.0);
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

            // Look for number of votes and question and update
            Label upVotesLabel = (Label) gridPane.lookup("#upVotesLabel");
            upVotesLabel.setText(item.getUpvotes() + " votes");

            Label questionLabel = (Label) gridPane.lookup("#questionLabel");

            // Check if exists
            if (questionLabel != null) {
                questionLabel.setText(item.getText());
            }

            Label ownerLabel = (Label) gridPane.lookup("#ownerLabel");
            ownerLabel.setText(item.getOwner());

            Label answerLabel = (Label) gridPane.lookup("#answerLabel");

            // Check if exists
            if (answerLabel != null) {
                answerLabel.setText("Answer: " + item.getAnswer());
            }

            // Show graphic representation
            setGraphic(anchorPane);
        }
    }
}
