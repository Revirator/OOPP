package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DeleteServerCommunication extends ServerCommunication {

    /** Deletes question corresponding to this id from database.
     * Makes DELETE request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to be deleted from database
     * @return boolean - true if DELETE operation succeeded, false otherwise.
     */
    public static boolean deleteQuestion(long questionId) {
        HttpRequest request = HttpRequest.newBuilder().DELETE()
                .uri(URI.create("http://localhost:8080/questions/" + questionId)).build();
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

    /** Sends the ID of the user that needs to be removed from the DB.
     * @param userId the ID of the user
     * @return true if the user has been removed from the DB, false otherwise
     */
    public static boolean removeUser(long userId) {
        HttpRequest request = HttpRequest.newBuilder().DELETE()
                .uri(URI.create("http://localhost:8080/users/remove/" + userId)).build();
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
}
