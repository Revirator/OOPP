package nl.tudelft.oopp.demo.cellfactory;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nl.tudelft.oopp.demo.data.User;

public class ParticipantCell extends ListCell<User> {

//    private AnchorPane anchorPane = new AnchorPane();
    private GridPane gridPane = new GridPane();

    public ParticipantCell() {

        createCell();
    }

    GridPane getGridPane() {
        return this.gridPane;
    }

    private void createCell() {

        // Label: "Role, nickname"
        Label userName = new Label();
        userName.setId("userNameLabel");
        userName.setAlignment(Pos.CENTER_LEFT);

        gridPane.add(userName, 0, 0);

        // Set background colours
        gridPane.styleProperty().setValue("-fx-background-color: white");
//        anchorPane.styleProperty().setValue("-fx-background-color: #E5E5E5");

        // Add wrapper to anchor pane
//        anchorPane.getChildren().add(gridPane);

        // Align cell wrapper in gridpane
        AnchorPane.setTopAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, 10.0);
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setBottomAnchor(gridPane, 10.0);



    }


    @Override
    public void updateItem(User user, boolean empty) {

        // Update listview
        super.updateItem(user, empty);

        // Empty list item
        if (empty || user == null) {

            setGraphic(null);
            setText("");

        } else { // Non-empty list item

            Label userName = (Label) gridPane.lookup("#userNameLabel");

            String updatedName = user.getRole() + ", " + user.getNickname();

            userName.setText(updatedName);

            setGraphic(gridPane);
        }

    }


}
