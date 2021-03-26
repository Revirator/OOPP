package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

public class PutServerCommunication extends ServerCommunication {

    /** Sends feedback to the server which is processed and the rooms are updated.
     * @param url the students link connected to a room
     * @param feedback the feedback we want to send
     */
    public static void sendFeedback(String url, String feedback) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/rooms/" + url + "/" + feedback))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Something went wrong! Feedback was not sent!");
            error.show();
        }
    }

    /** Sends a PUT request to the server to make a room inactive.
     * @param code the room link as a String
     */
    public static void updateRoomStatus(String code) {
        // Including the code in the body of the request and ..
        // .. not in the URL might be better, but I couldn't get it to work.
        String url = "http://localhost:8080/rooms/update/" + code;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Something went wrong!");
            error.show();
        }
    }

    /** Updates attribute "text" of question corresponding to this id in database.
     * Makes PUT request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to be updated in database
     * @return boolean - true if PUT operation succeeded, false otherwise.
     */
    public static boolean editQuestion(long questionId, String update) {
        if (update.equals("")) {
            return false;
        }

        String url = "http://localhost:8080/questions/" + questionId;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(update))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return false;
        }
        return true;
    }

    /** Updates attribute "isAnswered" of question corresponding to this id in database.
     * Makes PUT request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to be updated in database
     * @return boolean - true if PUT operation succeeded, false otherwise.
     */
    public static boolean markQuestionAsAnswered(long questionId) {
        String url = "http://localhost:8080/questions/markAnswered/" + questionId;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return false;
        }
        return true;
    }

    /** Updates attribute "answer" of question corresponding to this id in database.
     * Makes PUT request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to set answer of in database
     * @return boolean - true if PUT operation succeeded, false otherwise.
     */
    public static boolean setAnswer(long questionId, String answer) {
        if (answer.equals("")) {
            return false;
        }

        String url = "http://localhost:8080/questions/setanswer/" + questionId;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(answer))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return false;
        }
        return true;
    }

    /** Increments the upvote amount in server after question is upvoted on client
     * Makes PUT request to server to increment upvotes via QuestionController.
     * @param questionId - id of the question that will get its upvotes incremented
     */
    public static boolean upvoteQuestion(Long questionId) {
        String url = "http://localhost:8080/questions/upvote/" + questionId;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return false;
        }
        return true;
    }

    /** Undos incrementing the upvote amount in server after question is upvoted on client
     * Makes PUT request to server to undo incrementing upvotes via QuestionController.
     * @param questionId - id of the question that will get its upvotes decremented
     */
    public static boolean deUpvoteQuestion(Long questionId) {
        String url = "http://localhost:8080/questions/deupvote/" + questionId;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return false;
        }
        return true;
    }

    /** Sends the id of the student to be banned to the Server.
     * The banned field is updated to true and the student is kicked out of the lecture.
     * @param user the student to be banned
     */
    public static void banStudent(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create("http://localhost:8080/users/ban/" + user.getId()))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
    }
}
