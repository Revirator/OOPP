package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

public class ServerCommunication {

    protected static final HttpClient client = HttpClient.newBuilder().build();

    // Had to modify the serializer because the room entity uses LocalDateTime
    // and for some reason gson doesn't support that...
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();


    /** Retrieves a room from the server.
     * @param code room identification code
     * @param toLog indicates if the request should be logged
     * @return the body of the response from the server or null if the room does not exist.
     */
    public static Room getRoom(String code, boolean toLog) {
        return GetServerCommunication.getRoom(code, toLog);
    }


    /**
     * Sends room to the server, returns a room with URLs.
     * @param room primitive room
     * @return room with all parameters
     */
    public static Room makeRoom(Room room) {
        return PostServerCommunication.makeRoom(room);
    }


    /** Sends feedback to the server which is processed and the rooms are updated.
     * @param url the students link connected to a room
     * @param feedback the feedback we want to send
     */
    public static void sendFeedback(String url, String feedback) {
        PutServerCommunication.sendFeedback(url, feedback);
    }

    /** Sends a user to the server, who is saved in the DB ..
     * .. and added to the list of participants in the ..
     * .. corresponding room instance.
     * @param user the user to be saved in the DB
     * @param roomID the id of the room the user is a participant in
     * @return Long - the id of the user
     */
    public static Long sendUser(User user, long roomID) {
        return PostServerCommunication.sendUser(user, roomID);
    }

    /** Sends the ID of the user that needs to be removed from the DB.
     * @param userId the ID of the user
     * @return true if the user has been removed from the DB, false otherwise
     */
    public static boolean removeUser(long userId) {
        return DeleteServerCommunication.removeUser(userId);
    }

    /**
     * Fetches a list of all students.
     * @param roomID ID of the room
     * @return a list of all students in the room
     */
    public static List<Student> getStudents(long roomID) {
        return GetServerCommunication.getStudents(roomID);
    }

    /** Sends an id to the server.
     * The server return the student with the given id ..
     * .. or null if the student doesn't exist.
     * @param studentId the id of the student to be updated
     * @return User - a new instance of Student corresponding to the id
     */
    public static User getStudent(Long studentId) {
        return GetServerCommunication.getStudent(studentId);
    }

    /** Sends the id of the student to be banned to the Server.
     * The banned field is updated to true and the student is kicked out of the lecture.
     * @param user the student to be banned
     */
    public static void banStudent(User user) {
        PutServerCommunication.banStudent(user);
    }

    /** Sends a request to the server to check if the user's IP is ..
     * .. in the list of banned IPs for this lecture.
     * @param user the student we want to check
     * @return true if the user is banned or there is a server error and false otherwise
     */
    public static boolean checkIfBanned(User user) {
        return GetServerCommunication.checkIfBanned(user);
    }

    /**
     * Fetches a list of all moderators.
     * @param roomID ID of the room
     * @return a list of all moderators in the room
     */
    public static List<Moderator> getModerators(long roomID) {
        return GetServerCommunication.getModerators(roomID);
    }

    /**
     * Retrieves a list of all questions.
     * @param roomID that we want the questions from
     * @return list of all questions in that room
     */
    public static List<Question> getQuestions(long roomID) {
        return GetServerCommunication.getQuestions(roomID);
    }

    /**
     * Retrieves a list of all answered questions.
     * from the server for a specific room
     * @param roomID room identification code
     * @return the body of a get request to the server (list of questions).
     */
    public static List<Question> getAnsweredQuestions(long roomID) {
        return GetServerCommunication.getAnsweredQuestions(roomID);
    }

    /** Sends a PUT request to the server to make a room inactive.
     * @param code the room link as a String
     */
    public static void updateRoomStatus(String code) {
        PutServerCommunication.updateRoomStatus(code);
    }

    /** Deletes question corresponding to this id from database.
     * Makes DELETE request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to be deleted from database
     * @return boolean - true if DELETE operation succeeded, false otherwise.
     */
    public static boolean deleteQuestion(long questionId) {
        return DeleteServerCommunication.deleteQuestion(questionId);
    }

    /** Updates attribute "text" of question corresponding to this id in database.
     * Makes PUT request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to be updated in database
     * @return boolean - true if PUT operation succeeded, false otherwise.
     */
    public static boolean editQuestion(long questionId, String update) {
        return PutServerCommunication.editQuestion(questionId, update);
    }

    /** Updates attribute "isAnswered" of question corresponding to this id in database.
     * Makes PUT request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to be updated in database
     * @return boolean - true if PUT operation succeeded, false otherwise.
     */
    public static boolean markQuestionAsAnswered(long questionId) {
        return PutServerCommunication.markQuestionAsAnswered(questionId);
    }

    /** Makes POST request to server, to store new question in database.
     * Server will attach an id to this question (generated by db)
     * Handled by QuestionController.
     * @param newQuestion - new Question to be posted
     * @return Long - generated id for this question
     */
    public static Long postQuestion(Question newQuestion) {
        return PostServerCommunication.postQuestion(newQuestion);
    }

    /** Updates attribute "answer" of question corresponding to this id in database.
     * Makes PUT request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to set answer of in database
     * @return boolean - true if PUT operation succeeded, false otherwise.
     */
    public static boolean setAnswer(long questionId, String answer) {
        return PutServerCommunication.setAnswer(questionId, answer);
    }

    /** Increments the upvote amount in server after question is upvoted on client
     * Makes PUT request to server to increment upvotes via QuestionController.
     * @param questionId - id of the question that will get its upvotes incremented
     */
    public static boolean upvoteQuestion(Long questionId) {
        return PutServerCommunication.upvoteQuestion(questionId);
    }

    /** Undos incrementing the upvote amount in server after question is upvoted on client
     * Makes PUT request to server to undo incrementing upvotes via QuestionController.
     * @param questionId - id of the question that will get its upvotes decremented
     */
    public static boolean deUpvoteQuestion(Long questionId) {
        return PutServerCommunication.deUpvoteQuestion(questionId);
    }
}
