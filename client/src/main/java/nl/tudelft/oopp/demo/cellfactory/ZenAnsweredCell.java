package nl.tudelft.oopp.demo.cellfactory;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
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
        anchorPane.getChildren().add(gridPane);
        ColumnConstraints columnZeroConstraints = new ColumnConstraints();
        columnZeroConstraints.setPercentWidth(97);
        gridPane.getColumnConstraints().add(columnZeroConstraints);

        RowConstraints firstRow = new RowConstraints();
        firstRow.setPrefHeight(20);
        RowConstraints rowConstraints = new RowConstraints();
        gridPane.getRowConstraints().add(firstRow);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints);

        // Create all labels with ID
        Label questionLabel = new Label();
        questionLabel.setId("questionLabel");
        questionLabel.setPrefWidth(400);
        questionLabel.wrapTextProperty().setValue(true);

        Label ownerLabel = new Label();
        ownerLabel.setId("ownerLabel");
        ownerLabel.wrapTextProperty().setValue(true);
        ownerLabel.setTextFill(Paint.valueOf("#00A6D6"));

        Label answerLabel = new Label();
        answerLabel.setId("answerLabel");
        answerLabel.setPrefWidth(400);
        answerLabel.wrapTextProperty().setValue(true);
        answerLabel.setStyle("-fx-border-color: black");

        // Add elements to grid pane
        gridPane.add(ownerLabel, 0, 0);
        gridPane.add(questionLabel, 0,1);
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
