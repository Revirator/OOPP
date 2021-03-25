package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.cellfactory.ParticipantCell;
import nl.tudelft.oopp.demo.cellfactory.StudentAnsweredCell;
import nl.tudelft.oopp.demo.cellfactory.StudentQuestionCell;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.controllers.StudentRoomController;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.User;

public class StudentView extends AppView {

    /*
    Font sizes specific for student screen.
     */
    private DoubleProperty pollButtonFontSize = new SimpleDoubleProperty(10);

    /**
     * Creates the student screen scene and loads it on the primary stage.
     * @param primaryStage primary stage of the app
     * @throws IOException if FXMLLoader fails to load the url
     */
    @Override
    public void start(Stage primaryStage) {
        // Load file
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/studentRoom.fxml");
        loader.setLocation(xmlUrl);
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong! Could not load the room!");
            alert.show();
        }

        // StudentRoomController needs this StudentView for display
        StudentRoomController src = loader.getController();
        src.setData(super.getUser(), super.getRoom(), this);


        // Create new scene with root
        Scene scene = new Scene(root);

        // Set scene on primary stage
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create responsive lists
        linkLists(root, src);

        // Binds the font sizes relative to the screen size
        bindFonts(scene);
    }

    /**
     * Binds the font sizes for a responsive UI.
     * @param scene scene to make responsive
     */
    @Override
    public void bindFonts(Scene scene) {

        // Bind screen size to font size
        pollButtonFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));


        Parent root = scene.getRoot();

        // Put the font sizes on all according nodes
        for (Node node : root.lookupAll(".pollButton")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    pollButtonFontSize.asString(), ";"));
        }

        // Bind shared font sizes
        super.bindFonts(scene);
    }

    /**
     * Adds a question to the student view.
     * @param question question to add
     * @return true if successful, false if not
     */
    public boolean addQuestion(Question question) {

        ObservableList<Question> questions = super.getQuestions();

        // Not adding duplicates
        if (questions.contains(question)) {
            return false;
        }

        questions.add(question);

        // Sort based on votes
        questions.sort(Comparator.comparing(Question::getUpvotes, Comparator.reverseOrder()));

        return true;
    }

    /**
     * Bind the correct cells to the three list views.
     * @param root parent node of the view
     * @param roomController current room controller
     */
    public void bindCellFactory(Parent root, RoomController roomController) {

        ListView<Question> questionListView = (ListView<Question>) root.lookup("#questionListView");
        ListView<Question> answeredListView = (ListView<Question>) root.lookup("#answeredListView");
        ListView<User> participantsListView = (ListView<User>) root.lookup("#participantsListView");

        questionListView.setCellFactory(param ->
                new StudentQuestionCell(super.getQuestions(), super.getAnswered(), roomController));
        answeredListView.setCellFactory(param ->
                new StudentAnsweredCell(super.getAnswered(), roomController));
        participantsListView.setCellFactory(param -> new ParticipantCell());
    }


    /**
     * Launches the student view.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
