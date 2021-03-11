package nl.tudelft.oopp.demo.views;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.cellfactory.NoSelectionModel;
import nl.tudelft.oopp.demo.cellfactory.StudentAnsweredCell;
import nl.tudelft.oopp.demo.cellfactory.StudentQuestionCell;
import nl.tudelft.oopp.demo.data.Question;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentView extends Application {


    // Font sizes for student screen.
    private DoubleProperty subTitleFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty tabFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty pollButtonFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty buttonFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty textBoxFontSize = new SimpleDoubleProperty(10);

    // List of questions
    private ObservableList<Question> questions = FXCollections.observableArrayList();
    private ObservableList<Question> answered = FXCollections.observableArrayList();

    /**
     * Creates the student screen scene and loads it on the primary stage.
     * @param primaryStage primary stage of the app
     * @throws IOException if FXMLLoader fails to load the url
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Load file
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/studentRoom.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        // Create new scene with root
        Scene scene = new Scene(root);

        // Set scene on primary stage
        primaryStage.setScene(scene);
        primaryStage.show();

        ListView<Question> questionListView = (ListView<Question>) root.lookup("#questionListView");
        ListView<Question> answeredListView = (ListView<Question>) root.lookup("#answeredListView");

        questionListView.setItems(questions);
        answeredListView.setItems(answered);

//        DEBUGGING PURPOSES
//
//        addQuestion(new Question(20,20,"What's the square root of -1?","Student 1",20));
//
//        addQuestion(new Question(20,20,"Is Java a programming language?","Student 2",20));
//
//        addQuestion(new Question(20,20,"What is the idea behind the TU Delft logo?", "Student 3", 50));
//
//        for (Question q : questions) {
//            q.setAnswer("this is the answer!");
//        }

        // Set cell factory to use student cell
        questionListView.setCellFactory(param -> new StudentQuestionCell(questions, answered));
        answeredListView.setCellFactory(param -> new StudentAnsweredCell(answered));

        // Binds the font sizes relative to the screen size
        bindFonts(scene);

        /*
        Prevents list items from being selected
        whilst still allowing buttons to be pressed
         */
        questionListView.setSelectionModel(new NoSelectionModel<>());
        answeredListView.setSelectionModel(new NoSelectionModel<>());
    }

    /**
     * Binds the font sizes for a responsive UI.
     * @param scene scene to make responsive
     */
    private void bindFonts(Scene scene) {

        Parent root = scene.getRoot();

        subTitleFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(85));


        tabFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(85)));

        pollButtonFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));

        buttonFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(120)));

        textBoxFontSize.bind(Bindings.min(25,
                scene.widthProperty().add(scene.heightProperty()).divide(75)));

        // Put the font sizes on all according nodes
        for (Node node : root.lookupAll(".subTitleText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    subTitleFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".tab-label")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    tabFontSize.asString(), ";"));
        }


        for (Node node : root.lookupAll(".pollButton")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    pollButtonFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".buttonText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    buttonFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".textBox")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    textBoxFontSize.asString(), ";"));
        }

    }

    /**
     * Adds a question to the student view.
     * @param question question to add
     * @return true if successful, false if not
     */
    public boolean addQuestion(Question question) {

        // Not adding duplicates
        if (questions.contains(question)) return false;

        questions.add(question);

        // Sort based on votes
        questions.sort(Comparator.comparing(Question::getUpvotes, Comparator.reverseOrder()));

        return true;
    }



    public static void main(String[] args) {
        launch(args);
    }

}
