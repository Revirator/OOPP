package nl.tudelft.oopp.demo.cellfactory;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.data.Question;

public class ZenQuestionCell extends ListCell<Question> {

    private AnchorPane anchorPane = new AnchorPane();
    private GridPane gridPane = new GridPane();

    /**
     * Constructor for zen question cell.
     */
    public ZenQuestionCell() {
        super();

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


        // Add elements to grid pane
        gridPane.add(ownerLabel, 0, 0);
        gridPane.add(questionLabel, 0,1);
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
        }
    }

}
