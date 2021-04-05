package nl.tudelft.oopp.demo.cellfactory;

import java.net.URL;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import nl.tudelft.oopp.demo.controllers.ModeratorRoomController;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.data.Question;

public class ModeratorAnsweredCell extends ListCell<Question> {

    private AnchorPane anchorPane = new AnchorPane();
    private GridPane gridPane = new GridPane();
    private Question question;
    private ObservableList<Question> answered;
    private boolean editingQuestion;
    private boolean editingAnswer;
    private TextField editableQuestion;
    private TextField editableAnswer;
    private RoomController mrc;

    /**
     * Constructor for moderator answer cell.
     * @param answered ObservableList of answered questions
     */
    public ModeratorAnsweredCell(ObservableList<Question> answered,
                                 RoomController mrc) {
        super();

        this.answered = answered;
        editingAnswer = false;
        editingQuestion = false;
        editableAnswer = new TextField();
        editableQuestion = new TextField();
        this.mrc = mrc;

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
        columnOneConstraints.setPrefWidth(30);
        gridPane.getColumnConstraints().add(columnZeroConstraints);
        gridPane.getColumnConstraints().add(columnOneConstraints);

        RowConstraints firstRow = new RowConstraints();
        firstRow.setPrefHeight(30);
        RowConstraints rowConstraints = new RowConstraints();
        gridPane.getRowConstraints().add(firstRow);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints);

        // Create all labels with ID
        Label questionLabel = new Label();
        questionLabel.setId("questionLabel");
        questionLabel.setPrefWidth(325);
        questionLabel.wrapTextProperty().setValue(true);

        Label ownerLabel = new Label();
        ownerLabel.setId("ownerLabel");
        ownerLabel.wrapTextProperty().setValue(true);
        ownerLabel.setTextFill(Paint.valueOf("#00A6D6"));

        Label answerLabel = new Label();
        answerLabel.setId("answerLabel");
        answerLabel.setPrefWidth(325);
        answerLabel.wrapTextProperty().setValue(true);
        answerLabel.setStyle("-fx-border-color: black");

        // Create buttons
        Button editAnswerButton = new Button();
        editAnswerButton.setId("editAnswer");
        Button editQuestionButton = new Button();
        editQuestionButton.setId("editQuestion");
        Button deleteButton = new Button();
        deleteButton.setId("deleteButton");

        editAnswerButton.setPrefWidth(25);
        URL path = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
        editAnswerButton.setTooltip(new Tooltip("Edit answer"));
        setButtonStyle(editAnswerButton, path);
        editAnswerButton.setCursor(Cursor.HAND);

        editQuestionButton.setPrefWidth(25);
        path = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
        editQuestionButton.setTooltip(new Tooltip("Edit question"));
        setButtonStyle(editQuestionButton, path);
        editQuestionButton.setCursor(Cursor.HAND);

        deleteButton.setPrefWidth(28);
        path = StudentQuestionCell.class.getResource("/images/redTrash.png");
        setButtonStyle(deleteButton, path);
        deleteButton.setCursor(Cursor.HAND);

        // Create wrappers
        HBox answerWrapper = new HBox(answerLabel, editAnswerButton);
        HBox questionWrapper = new HBox(questionLabel, editQuestionButton);

        // Set wrapper spacing
        answerWrapper.setSpacing(10);
        questionWrapper.setSpacing(10);

        // Add elements to grid pane
        gridPane.add(ownerLabel, 0, 0);
        gridPane.add(questionWrapper, 0,1);
        gridPane.add(answerWrapper, 0,2);
        gridPane.add(deleteButton, 1, 0);

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

        // Click events for buttons

        editQuestionButton.setOnAction(event -> {

            if (this.question == null) {
                return;
            }

            questionWrapper.getChildren().clear();

            // User saves changes
            if (editingQuestion) {

                // Send changes to server
                mrc.editQuestion(
                        this.question, editableQuestion.getText());

                questionWrapper.getChildren().addAll(questionLabel, editQuestionButton);
                question.setText(editableQuestion.getText());
                editQuestionButton.setTooltip(new Tooltip("Edit question"));
                editQuestionButton.setPrefWidth(25);
                URL url = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
                setButtonStyle(editQuestionButton, url);
                questionLabel.setText(editableQuestion.getText());
                editingQuestion = false;

            } else { // User wants to make changes

                questionWrapper.getChildren().addAll(editableQuestion, editQuestionButton);
                editableQuestion.setText(question.getText());
                editQuestionButton.setTooltip(new Tooltip("Save changes"));
                editQuestionButton.setPrefWidth(27);
                URL url = StudentQuestionCell.class.getResource("/images/checkGreen.png");
                setButtonStyle(editQuestionButton, url);
                editingQuestion = true;

            }
        });

        editAnswerButton.setOnAction(event -> {

            if (this.question == null) {
                return;
            }

            answerWrapper.getChildren().clear();

            // User saves changes
            if (editingAnswer) {

                ((ModeratorRoomController) mrc).setAnswer(this.question,
                        editableAnswer.getText());

                answerWrapper.getChildren().addAll(answerLabel, editAnswerButton);
                question.setAnswer(editableAnswer.getText());
                editAnswerButton.setTooltip(new Tooltip("Edit answer"));
                editAnswerButton.setPrefWidth(25);
                URL url = StudentQuestionCell.class.getResource("/images/colouredPencil.png");
                setButtonStyle(editAnswerButton, url);
                answerLabel.setText("Answer: " + editableAnswer.getText());
                editingAnswer = false;

            } else { // User wants to make changes

                answerWrapper.getChildren().addAll(editableAnswer, editAnswerButton);
                editableAnswer.setText(question.getAnswer());
                editAnswerButton.setTooltip(new Tooltip("Save changes"));
                editAnswerButton.setPrefWidth(27);
                URL url = StudentQuestionCell.class.getResource("/images/checkGreen.png");
                setButtonStyle(editAnswerButton, url);
                editingAnswer = true;

            }
        });

        deleteButton.setOnAction(event -> {

            mrc.deleteQuestion(this.question);

            // Remove question from list
            answered.remove(question);

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


    private void setButtonStyle(Button button, URL path) {
        button.setStyle("-fx-background-image: url('" + path + "');"
                + " -fx-background-repeat: no-repeat;"
                + " -fx-background-size: 100% 100%;");
    }
}
