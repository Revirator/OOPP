package nl.tudelft.oopp.demo.cellfactory;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.controllers.ModeratorRoomController;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

public class ModeratorParticipantCell extends ParticipantCell {

    /**
     * Constructor for a moderator participant cell.
     */
    public ModeratorParticipantCell(String windowOwner) {
        super(windowOwner);
        // addButtons();
    }

    private void addButtons() {
        Button ipBanButton = new Button("Ban");

        ipBanButton.setId("ipBanButton");

        // Wrapper for all elements
        HBox cellWrap = new HBox(ipBanButton);
        cellWrap.setSpacing(10);

        super.getGridPane().add(cellWrap, 1, 0);

        cellWrap.setAlignment(Pos.CENTER_LEFT);

        // CLick event for ipBanButton
        ipBanButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to ban\n"
                    + super.getUser().getNickname() + " ?");
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")) {
                ServerCommunication.banStudent(super.getUser());
            }
        });

    }

}
