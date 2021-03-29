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
}
