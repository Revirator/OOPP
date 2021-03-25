package nl.tudelft.oopp.demo.cellfactory;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.User;

public class ParticipantCell extends ListCell<User> {

    private GridPane gridPane = new GridPane();
    private String windowOwner;
    private User user;

    public ParticipantCell(String windowOwner) {
        this.windowOwner = windowOwner;
        createCell();
    }

    GridPane getGridPane() {
        return this.gridPane;
    }

    User getUser() {
        return this.user;
    }

    public String getWindowOwner() {
        return windowOwner;
    }

    private void createCell() {

        // Label: "Role, nickname"
        Label userName = new Label();
        userName.setId("userNameLabel");
        userName.setAlignment(Pos.CENTER_LEFT);

        gridPane.add(userName, 0, 0);

        // Set background colours
        gridPane.styleProperty().setValue("-fx-background-color: white");

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

            this.user = user;

            if (this.windowOwner.equals("Moderator") && this.user.getRole().equals("Student")) {
                Button ipBanButton = new Button("Ban");

                ipBanButton.setId("ipBanButton");

                // Wrapper for all elements
                HBox cellWrap = new HBox(ipBanButton);
                cellWrap.setSpacing(10);

                this.gridPane.add(cellWrap, 1, 0);

                cellWrap.setAlignment(Pos.CENTER_LEFT);

                // CLick event for ipBanButton
                ipBanButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to ban\n"
                            + this.user.getNickname() + " ?");
                    alert.showAndWait();
                    if (alert.getResult().getText().equals("OK")) {
                        ServerCommunication.banStudent(this.user);
                    }
                });
            }

            Label userName = (Label) gridPane.lookup("#userNameLabel");

            String updatedName = user.getRole() + ", " + user.getNickname();

            userName.setText(updatedName);

            setGraphic(gridPane);
        }

    }

}
