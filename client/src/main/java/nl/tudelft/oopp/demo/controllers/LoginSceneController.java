package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Quote;

public class LoginSceneController {


    @FXML
    private TextField search;       // field name corresponds to fx:id
    @FXML
    private ListView<Quote> quoteList;

    /**
     * Handles clicking the button.
     * Returns list of quotes containing search keyword.
     */
    public void buttonClicked() {
        List<Quote> result = ServerCommunication.findQuotes(search.getText());
        quoteList.getItems().clear();
        quoteList.getItems().addAll(result);
    }
}
