package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudentSceneController {

    @FXML
    private Label liveFeedback;
    private String userName;

    public void setUserName(String name) {
        this.userName = name;
    }
}
