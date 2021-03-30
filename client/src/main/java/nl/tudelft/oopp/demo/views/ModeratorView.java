package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.cellfactory.ModeratorAnsweredCell;
import nl.tudelft.oopp.demo.cellfactory.ModeratorParticipantCell;
import nl.tudelft.oopp.demo.cellfactory.ModeratorQuestionCell;
import nl.tudelft.oopp.demo.controllers.ModeratorRoomController;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.User;

public class ModeratorView extends AppView {

    /*
    Font sizes specific for moderator screen.
     */
    private DoubleProperty percentageFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty normalFontSize = new SimpleDoubleProperty(10);

    /**
     * Creates the moderator screen scene and loads it on the primary stage.
     * @param primaryStage primary stage of the app
     */
    @Override
    public void start(Stage primaryStage) {
        // Load file
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/moderatorRoom.fxml");
        loader.setLocation(xmlUrl);
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong! Could not load the room");
            alert.show();
        }
        ModeratorRoomController mrc = loader.getController();
        mrc.setData(super.getUser(), super.getRoom(), this);

        // Create new scene with root
        Scene scene = new Scene(root);

        // Set scene on primary stage
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        primaryStage.show();

        // Create responsive lists
        linkLists(root, mrc);

        // Binds the font sizes relative to the screen size
        bindFonts(scene);
    }

    /**
     * Bind the correct cells to the list views.
     * @param root parent node of the view
     * @param roomController current room controller
     */
    public void bindCellFactory(Parent root, RoomController roomController) {
        // Look up all list views
        ListView<Question> questionListView = (ListView<Question>) root.lookup("#questionListView");
        ListView<Question> answeredListView = (ListView<Question>) root.lookup("#answeredListView");
        ListView<User> participantsListView = (ListView<User>) root.lookup("#participantsListView");

        // Set cell creation per list view
        questionListView.setCellFactory(param ->
                new ModeratorQuestionCell(super.getQuestions(),
                        super.getAnswered(), roomController));
        answeredListView.setCellFactory(param ->
                new ModeratorAnsweredCell(super.getAnswered(), roomController));
        participantsListView.setCellFactory(param -> new ModeratorParticipantCell());
    }

    /**
     * Makes the font responsive to screen size.
     * @param scene current scene
     */
    @Override
    public void bindFonts(Scene scene) {
        // Bind screen size to font
        percentageFontSize.bind(Bindings.min(45,
                scene.widthProperty().add(scene.heightProperty()).divide(45)));


        normalFontSize.bind(Bindings.min(18,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));

        Parent root = scene.getRoot();

        // Put the font sizes on all according nodes
        for (Node node : root.lookupAll(".normalText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    normalFontSize.asString(), ";"));
        }


        for (Node node : root.lookupAll(".percentage")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    percentageFontSize.asString(), ";"));
        }

        // Bind shared fonts
        super.bindFonts(scene);
    }

    /**
     * Most upvoted questions are displayed on top for Moderator.
     * @param questionList list of current questions
     * @param answeredList list of current answered questions
     */
    @Override
    public void update(List<Question> questionList, List<Question> answeredList) {
        super.update(questionList, answeredList);
        questions.sort(Comparator.comparing(Question::getUpvotes, Comparator.reverseOrder()));
    }

    /**
     * Launches this view.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
