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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.cellfactory.ModeratorAnsweredCell;
import nl.tudelft.oopp.demo.cellfactory.ModeratorQuestionCell;
import nl.tudelft.oopp.demo.cellfactory.NoSelectionModel;
import nl.tudelft.oopp.demo.cellfactory.ParticipantCell;
import nl.tudelft.oopp.demo.controllers.ModeratorRoomController;
import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

public class ModeratorView extends Application {

    /**
     * Font sizes for moderator screen.
     */
    private DoubleProperty subTitleFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty tabFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty percentageFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty buttonFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty textBoxFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty normalFontSize = new SimpleDoubleProperty(10);

    private User moderator;
    private Room room;


    // List of questions
    private ObservableList<Question> questions = FXCollections.observableArrayList();
    private ObservableList<Question> answered = FXCollections.observableArrayList();
    private ObservableList<User> participants = FXCollections.observableArrayList();


    /** Used in SplashController to pass the user and the room object.
     * @param moderator the moderator that is using the window
     * @param room the room corresponding to the code entered
     */
    public void setData(User moderator, Room room) {
        this.moderator = moderator;
        this.room = room;
    }

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
        mrc.setData(moderator, room, this);

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

        // addUser(new Student("ddd", null));
        // addUser(new Moderator("xyz", null));
        // addUser(new Student("abc", null));


        // Set cell factory to use student cell
        questionListView.setCellFactory(param ->
                new ModeratorQuestionCell(questions, answered, mrc));
        answeredListView.setCellFactory(param ->
                new ModeratorAnsweredCell(answered, mrc));
        participantsListView.setCellFactory(param ->
                new ParticipantCell(this.moderator.getRole()));

        // Binds the font sizes relative to the screen size
        bindFonts(scene);

        /*
        Prevents list items from being selected
        whilst still allowing buttons to be pressed
         */
        questionListView.setSelectionModel(new NoSelectionModel<>());
        answeredListView.setSelectionModel(new NoSelectionModel<>());
        participantsListView.setSelectionModel(new NoSelectionModel<>());


        // Add choice boxes to screen
        createChoiceBoxes(scene);

        // Make fonts responsive
        bindFonts(scene);

    }

    /**
     * Adds a question to the list of current questions.
     * @param question question to add
     * @return true if successful, false if unsuccessful
     */
    public boolean addQuestion(Question question) {

        // Not adding duplicates
        if (questions.contains(question)) {
            return false;
        }

        // Add question
        questions.add(question);

        // Sort based on votes
        questions.sort(Comparator.comparing(Question::getUpvotes, Comparator.reverseOrder()));

        return true;
    }


    /**
     * Updates the questions and answered lists.
     * @param questionList all questions
     * @param answeredList all answered questions
     *      If a question in questionList (returned by server) exists, it will only be updated.
     *      Else, isOwner and hasVoted would be set to false again. (don't exist on server-side)
     */
    public void update(List<Question> questionList, List<Question> answeredList) {

        answered.clear();
        answered.addAll(answeredList);

        // questionList contains both answered and non-answered questions!
        for (Question q : questionList) {

            Question toUpdate = searchQuestion(q.getId());

            // if question exists and is NOT answered, update its values.
            if (toUpdate != null) {
                if (answered.contains(toUpdate)) {
                    questions.remove(toUpdate);
                } else {
                    toUpdate.setUpvotes(q.getUpvotes());
                    toUpdate.setText(q.getText());
                    toUpdate.setAnswer(q.getAnswer());
                }
                // if new question, just add it to the questions.
            } else if (!answered.contains(q)) {
                questions.add(q);
            }
        }

        questions.sort(Comparator.comparing(Question::getTime, Comparator.naturalOrder()));
        answered.sort(Comparator.comparing(Question::getTime, Comparator.reverseOrder()));

    }

    /**
     * Checks if this question id exists in the questionList.
     * @param questionId question id to check
     * @return true if exists, else false.
     */
    private Question searchQuestion(long questionId) {

        for (Question q : questions) {
            if (q.getId() == questionId) {
                return q;
            }
        }
        return null;
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

        participants.add(user);
        participants.sort(Comparator.comparing(User::getNickname));
        participants.sort(Comparator.comparing(User::getRole));

        return true;
    }


    /**
     * Updates the participant list.
     * @param studentList list of all students
     * @param moderatorList list of all moderators
     */
    public void updateParticipants(List<Student> studentList, List<Moderator> moderatorList) {

        participants.clear();
        /*
        for (User s : participants) {
            if (s.getRole().equals("Student") && ((Student) s).isBanned()) {
                participants.remove(s);
            }
        }

        for (Student s : studentList) {
            s.setRoom(this.room);
            if (!participants.contains(s) && !s.isBanned()) {
                participants.add(s);
            }
        }

        for (Moderator m : moderatorList) {
            m.setRoom(this.room);
            if (!participants.contains(m)) {
                participants.add(m);
            }
        }
        */

        studentList.sort(Comparator.comparing(Student::getNickname));
        moderatorList.sort(Comparator.comparing(Moderator::getNickname));
        participants.addAll(moderatorList);
        participants.addAll(studentList);

        // participants.sort(Comparator.comparing(User::getNickname));
        // participants.sort(Comparator.comparing(User::getRole));

    }


    /**
     * Creates the choice boxes for polls.
     * @param scene current scene
     */
    private void createChoiceBoxes(Scene scene) {

        Parent root = scene.getRoot();

        // Add options to dropdown buttons
        ChoiceBox<String> answers = (ChoiceBox) root.lookup("#answerAmount");
        ChoiceBox<String> correctAnswer = (ChoiceBox) root.lookup("#correctAnswer");

        for (int i = 1; i < 11; i++) {
            answers.getItems().add(String.valueOf(i));
        }

        for (char letter = 'A'; letter <= 'J'; letter++) {
            correctAnswer.getItems().add(String.valueOf(letter));
        }

    }

    /**
     * Binds the screen size to the font size to make responsive UI.
     * @param scene current scene
     */
    private void bindFonts(Scene scene) {

        // Bind font sizes to screen size
        subTitleFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(85));


        tabFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(85)));

        percentageFontSize.bind(Bindings.min(45,
                scene.widthProperty().add(scene.heightProperty()).divide(45)));

        buttonFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(120)));

        textBoxFontSize.bind(Bindings.min(18,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));

        normalFontSize.bind(Bindings.min(18,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));

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


        for (Node node : root.lookupAll(".normalText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    normalFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".buttonText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    buttonFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".textBox")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    textBoxFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".percentage")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    percentageFontSize.asString(), ";"));
        }
    }




    /**
     * Launches this view.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
