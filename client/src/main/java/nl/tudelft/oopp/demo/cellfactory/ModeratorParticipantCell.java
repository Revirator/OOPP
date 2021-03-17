package nl.tudelft.oopp.demo.cellfactory;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ModeratorParticipantCell extends ParticipantCell {

    /**
     * Constructor for a moderator participant cell.
     */
    public ModeratorParticipantCell() {
        super();

        addButtons();
    }

    private void addButtons() {

        Button kickButton = new Button("Kick");
        Button ipBanButton = new Button("IP-Ban");

        kickButton.setId("kickButton");
        ipBanButton.setId("ipBanButton");

        // Wrapper for all elements
        HBox cellWrap = new HBox(kickButton, ipBanButton);
        cellWrap.setSpacing(10);

        super.getGridPane().add(cellWrap, 1, 0);

        cellWrap.setAlignment(Pos.CENTER_RIGHT);


        // Click event for kick button
        kickButton.setOnAction(event -> {

            //TODO kick user

        });

        // CLick event for ipBanButton
        ipBanButton.setOnAction(event -> {

            //TODO ip-ban user

        });

    }

}
