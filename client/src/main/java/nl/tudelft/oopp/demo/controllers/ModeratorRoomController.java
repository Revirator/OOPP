package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.views.ModeratorView;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@EnableScheduling
public class ModeratorRoomController {

    private String name;
    private Room room;

    @FXML
    private AnchorPane anchor;

    /**
     * Used in SplashController to pass the username and the room object.
     * @param name the name entered by the user in splash
     * @param room the room corresponding to the code entered
     */
    public void setData(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    @Scheduled(fixedRate = 5000)
    public void updateQuestions() {
        Stage primaryStage = (Stage) anchor.getScene().getWindow();
        List<Question> questionList = ServerCommunication.getQuestions(room);
        List<Question> answeredList = ServerCommunication.getAnsweredQuestions(room);
        ModeratorView.update(primaryStage, questionList, answeredList);
    }
}
