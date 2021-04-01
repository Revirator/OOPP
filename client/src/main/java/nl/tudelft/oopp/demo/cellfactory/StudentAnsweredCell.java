package nl.tudelft.oopp.demo.cellfactory;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.data.Question;

public class StudentAnsweredCell extends ListCell<Question> {

    private AnchorPane anchorPane = new AnchorPane();
    private GridPane gridPane = new GridPane();
    private Question question;
    private ObservableList<Question> answered;
    private RoomController src;

    /** Initialized for each question by StudentView.
     * Constructor for student answer cell.
     * @param answered ObservableList of all answered questions
     */
    public StudentAnsweredCell(ObservableList<Question> answered, RoomController src) {
        super();

        this.answered = answered;
        this.src = src;

        // Create visual cell
        createCell();
    }

    /**
     * Creates a cell for answered questions.
     */
    private void createCell() {

        // Add grid pane to anchor pane
        anchorPane.getChildren().add(gridPane);
        ColumnConstraints columnZeroConstraints = new ColumnConstraints();
        columnZeroConstraints.setPrefWidth(330);
        columnZeroConstraints.setPercentWidth(90);
        ColumnConstraints columnOneConstraints = new ColumnConstraints();
        columnOneConstraints.setPrefWidth(20);
        gridPane.getColumnConstraints().add(columnZeroConstraints);
        gridPane.getColumnConstraints().add(columnOneConstraints);

        RowConstraints firstRow = new RowConstraints();
        firstRow.setPrefHeight(15);
        RowConstraints middleRows = new RowConstraints();
        middleRows.setPrefHeight(60);
        gridPane.getRowConstraints().add(firstRow);
        gridPane.getRowConstraints().add(middleRows);
        gridPane.getRowConstraints().add(middleRows);

        // Create all labels with ID's
        Label questionLabel = new Label();
        questionLabel.setId("questionLabel");
        questionLabel.setPrefWidth(440);
        questionLabel.wrapTextProperty().setValue(true);

        Label upVotesLabel = new Label();
        upVotesLabel.setId("upVotesLabel");

        Label ownerLabel = new Label();
        ownerLabel.setId("ownerLabel");

        HBox ownerUpVotesWrapper = new HBox(ownerLabel, upVotesLabel);
        ownerUpVotesWrapper.setSpacing(260);

        Label answerLabel = new Label();
        answerLabel.setId("answerLabel");
        answerLabel.setPrefWidth(440);
        answerLabel.wrapTextProperty().setValue(true);

        // Add elements to grid pane
        gridPane.add(ownerUpVotesWrapper, 0, 0);
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
     * @param empty true if empty, false if not empty
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

            // Update all labels
            Label upVotesLabel = (Label) gridPane.lookup("#upVotesLabel");
            upVotesLabel.setText(item.getUpvotes() + " votes");

            Label questionLabel = (Label) gridPane.lookup("#questionLabel");
            questionLabel.setText(item.getText());

            Label ownerLabel = (Label) gridPane.lookup("#ownerLabel");
            ownerLabel.setText(item.getOwner());

            Label answerLabel = (Label) gridPane.lookup("#answerLabel");
            answerLabel.setText("Answer: " + item.getAnswer());

            // Show graphic representation
            setGraphic(anchorPane);
        }
    }

}
