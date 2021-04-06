package nl.tudelft.oopp.demo.views;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.cellfactory.NoSelectionModel;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

public abstract class AppView extends MainView {

    /*
    All shared fonts.
     */
    private DoubleProperty tabFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty textBoxFontSize = new SimpleDoubleProperty(10);

    /*
    Observable lists for questions, answered questions and participants.
     */
    protected ObservableList<Question> questions = FXCollections.observableArrayList();
    protected ObservableList<Question> answered = FXCollections.observableArrayList();
    private ObservableList<User> participants = FXCollections.observableArrayList();

    private User user;
    private Room room;

    /** Used in SplashController to pass the user and the room object.
     * @param user the user that is using the window
     * @param room the room corresponding to the code entered
     */
    public void setData(User user, Room room) {
        this.user = user;
        this.room = room;
    }

    /**
     * Creates a scene and starts it.
     * @param primaryStage primary stage of the app
     */
    @Override
    public abstract void start(Stage primaryStage);

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
     * Updates the questions and answered questions.
     * @param questionList list of current questions (also containing all answered questions)
     * @param answeredList list of current answered questions
     */
    public void update(List<Question> questionList, List<Question> answeredList) {

        // remove deleted (non-answered) questions from view
        Iterator<Question> queIterator = questions.iterator();
        while (queIterator.hasNext()) {
            Question q = queIterator.next();
            if (!questionList.contains(q)) {
                queIterator.remove();
            }
        }

        // remove deleted (answered) questions from view
        // also uses questionList to check, because this contains all questions
        Iterator<Question> ansIterator = answered.iterator();
        while (ansIterator.hasNext()) {
            Question q = ansIterator.next();
            if (!questionList.contains(q)) { // Why not answeredList?
                ansIterator.remove();
            }
        }

        // check for every question in the updated list (both answered/unanswered)
        for (Question q : questionList) {

            // previous version of questions
            Question queToUpdate = searchQuestion(q.getId());
            Question ansToUpdate = searchAnswered(q.getId());

            // if new question, add to questions
            if (queToUpdate == null && ansToUpdate == null) {
                questions.add(q);

            } else if (answeredList.contains(q) && queToUpdate != null) {
                // if question recently answered, move to answered
                questions.remove(q);
                answered.add(q);

            } else if (answeredList.contains(q) && ansToUpdate != null) {
                // update values question in answers
                ansToUpdate.setUpvotes(q.getUpvotes());
                ansToUpdate.setText(q.getText());
                ansToUpdate.setAnswer(q.getAnswer());

            } else if (questionList.contains(q) && queToUpdate != null) {
                // update values question in questions
                queToUpdate.setUpvotes(q.getUpvotes());
                queToUpdate.setText(q.getText());
                queToUpdate.setAnswer(q.getAnswer());
                queToUpdate.setIsBeingAnswered(q.isBeingAnswered());
            }
        }

        // newly answered questions are on top
        answered.sort(Comparator.comparing(Question::getTime, Comparator.reverseOrder()));

    }

    /**
     * Checks if this question id exists in the questionList.
     * @param questionId question id to check
     * @return question if exists, else null
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
     * Checks if this question id exists in the answeredList.
     * @param questionId question id to check
     * @return question if exists, else null
     */
    private Question searchAnswered(long questionId) {

        for (Question q : answered) {
            if (q.getId() == questionId) {
                return q;
            }
        }
        return null;
    }

    /**
     * Links all observable lists to the corresponding
     * list views.
     * @param root parent node of the view
     * @param roomController current room controller
     */
    public void linkLists(Parent root, RoomController roomController) {
        // Look up all list views
        ListView<Question> questionListView = (ListView<Question>) root.lookup("#questionListView");
        ListView<Question> answeredListView = (ListView<Question>) root.lookup("#answeredListView");
        ListView<User> participantsListView = (ListView<User>) root.lookup("#participantsListView");

        // Links the list views to the corresponding list
        questionListView.setItems(questions);
        answeredListView.setItems(answered);
        participantsListView.setItems(participants);

        /*
        Custom selection model:
            Prevents text from disappearing when a cell is clicked
         */
        questionListView.setSelectionModel(new NoSelectionModel<>());
        answeredListView.setSelectionModel(new NoSelectionModel<>());
        participantsListView.setSelectionModel(new NoSelectionModel<>());

        // Bind the correct cells to the list views
        bindCellFactory(roomController);
    }

    /**
     * Getter for the observable list of questions.
     * @return ObservableList of questions
     */
    public ObservableList<Question> getQuestions() {
        return questions;
    }

    /**
     * Getter for the observable list of answered questions.
     * @return ObservableList of questions
     */
    public ObservableList<Question> getAnswered() {
        return answered;
    }

    /**
     * Setter that's only used in testing.
     * @param questions ObservableList of questions
     */
    public void setQuestions(ObservableList<Question> questions) {
        this.questions = questions;
    }

    /**
     * Setter that's only used in testing.
     * @param answered ObservableList of answered questions
     */
    public void setAnswered(ObservableList<Question> answered) {
        this.answered = answered;
    }

    /**
     * Getter for the current user.
     * @return current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for the current room.
     * @return current room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Binds the correct cells to all three list views.
     * Implemented by children because they have different cells.
     * @param roomController current room controller
     */
    public abstract void bindCellFactory(RoomController roomController);

    /**
     * Makes all font sizes responsive in the UI.
     * @param scene current scene
     */
    @Override
    public void bindFonts(Scene scene) {

        tabFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(85)));

        textBoxFontSize.bind(Bindings.min(25,
                scene.widthProperty().add(scene.heightProperty()).divide(85)));

        Parent root = scene.getRoot();

        // Put the font sizes on all according nodes
        for (Node node : root.lookupAll(".tab-label")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    tabFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".textBox")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    textBoxFontSize.asString(), ";"));
        }

        // Bind shared fonts
        super.bindFonts(scene);
    }
}
