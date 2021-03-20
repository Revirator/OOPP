package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.cellfactory.NoSelectionModel;
import nl.tudelft.oopp.demo.cellfactory.ParticipantCell;
import nl.tudelft.oopp.demo.cellfactory.StudentAnsweredCell;
import nl.tudelft.oopp.demo.cellfactory.StudentQuestionCell;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.controllers.StudentRoomController;
import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

public class StudentView extends Application {

    /**
     * Font sizes for student screen.
     */
    private DoubleProperty subTitleFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty tabFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty pollButtonFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty buttonFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty textBoxFontSize = new SimpleDoubleProperty(10);

    // List of questions
    private ObservableList<Question> questions = FXCollections.observableArrayList();
    private ObservableList<Question> answered = FXCollections.observableArrayList();
    private ObservableList<User> participants = FXCollections.observableArrayList();

    private User student;
    private Room room;



    /** Used in SplashController to pass the user and the room object.
     * @param student the student that is using the window
     * @param room the room corresponding to the code entered
     */
    public void setData(User student, Room room) {
        this.student = student;
        this.room = room;
    }

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
        src.setData(student, room, this);


        // Create new scene with root
        Scene scene = new Scene(root);

        // Set scene on primary stage
        primaryStage.setScene(scene);
        primaryStage.show();

        ListView<Question> questionListView = (ListView<Question>) root.lookup("#questionListView");
        ListView<Question> answeredListView = (ListView<Question>) root.lookup("#answeredListView");
        ListView<User> participantsListView = (ListView<User>) root.lookup("#participantsListView");

        questionListView.setItems(questions);
        answeredListView.setItems(answered);
        participantsListView.setItems(participants);

        // DEBUGGING PURPOSES

        addQuestion(new Question(1,20,
                "What's the square root of -1?","Senne",20, true));

        addQuestion(new Question(2,20,
                "Is Java a programming language?","Albert",20, false));

        addQuestion(new Question(3,20,
                "What is the idea behind the TU Delft logo?", "Henkie", 50, false));

        for (Question q : questions) {
            q.setAnswer("This is the answer!");
        }



        addUser(new Student("ddd", null));
        addUser(new Moderator("xyz", null));
        addUser(new Student("abc", null));

        // Set cell factory to use student cell
        questionListView.setCellFactory(param -> new StudentQuestionCell(questions, answered, src));
        answeredListView.setCellFactory(param -> new StudentAnsweredCell(answered, src));
        participantsListView.setCellFactory(param -> new ParticipantCell());

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


        subTitleFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(85));


        tabFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(85)));

        pollButtonFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));

        buttonFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(120)));

        textBoxFontSize.bind(Bindings.min(25,
                scene.widthProperty().add(scene.heightProperty()).divide(75)));

        Parent root = scene.getRoot();

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
        if (questions.contains(question)) {
            return false;
        }

        questions.add(question);

        // Sort based on votes
        questions.sort(Comparator.comparing(Question::getUpvotes, Comparator.reverseOrder()));

        return true;
    }

    /**
     * Adds a user to the observable list of participants.
     * @param user user to add
     * @return true if successful, false otherwise
     */
    public boolean addUser(User user) {

        if (participants.contains(user)) {
            return false;
        }

        // uncomment after Nadine's MR
        // this.room.addParticipant(user);
        participants.add(user);
        participants.sort(Comparator.comparing(User::getNickname));
        participants.sort(Comparator.comparing(User::getRole));

        return true;
    }

    /**
     * Updates the questions and answered lists.
     * @param questionList all questions
     * @param answeredList all answered questions
     */
    public void update(List<Question> questionList, List<Question> answeredList) {

        questions.clear();
        answered.clear();

        questions.addAll(questionList);
        answered.addAll(answeredList);

        questions.sort(Comparator.comparing(Question::getTime, Comparator.naturalOrder()));
        answered.sort(Comparator.comparing(Question::getTime, Comparator.reverseOrder()));

    }

    /**
     * Launches the student view.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
