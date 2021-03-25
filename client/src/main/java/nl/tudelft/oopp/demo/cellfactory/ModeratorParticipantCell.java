package nl.tudelft.oopp.demo.cellfactory;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

public class ModeratorParticipantCell extends ParticipantCell {

    /**
     * Constructor for a moderator participant cell.
     */
    public ModeratorParticipantCell() {
        super();
        addBanButton();
    }

    /**
     * Adds the ban button to the cell.
     */
    private void addBanButton() {
        Button ipBanButton = new Button("Ban");

        ipBanButton.setId("ipBanButton");
        ipBanButton.setCursor(Cursor.HAND);

        // Wrapper for all elements
        HBox cellWrap = new HBox(ipBanButton);
        cellWrap.setSpacing(10);

        super.getGridPane().add(cellWrap, 1, 0);
        super.getGridPane().setHgap(20);

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

    /**
     * Updates the cell.
     * @param user user in this cell
     * @param empty true if empty, false if not
     */
    @Override
    public void updateItem(User user, boolean empty) {

        super.updateItem(user, empty);

        if (!empty && user != null) {

            Button button = (Button) super.getGridPane().lookup("#ipBanButton");

            // Set button visible in cell only if student
            button.setVisible(user instanceof Student);

        }
    }

}
