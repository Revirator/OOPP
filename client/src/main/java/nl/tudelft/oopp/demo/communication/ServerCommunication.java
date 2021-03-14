package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;

public class ServerCommunication {

    private static final HttpClient client = HttpClient.newBuilder().build();

    // Had to modify the serializer because the room entity uses LocalDateTime
    // and for some reason gson doesn't support that...
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();

    /**
     * Retrieves a room from the server.
     * @param code room identification code
     * @return the body of a get request to the server (a room object).
     */
    public static Room getRoom(String code) {

        if (code.equals("")) {      // Some empty string check
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/rooms/" + code)).build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return null;
        }
        return gson.fromJson(response.body(), Room.class);
    }

    /**
     * Sends room to the server, returns a room with URLs.
     * @param room primitive room
     * @return room with all parameters
     */
    public static Room makeRoom(Room room) {

        if (room == null) {
            return null;
        }

        // not the best way to do it (goes wrong if someone adds ", " in one of the fields
        String postRequestBody = room.getRoomName() + ", "
                + room.getStartingTime() + ", " + room.isActive();

        // send request to the server
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/rooms"))
                .POST(HttpRequest.BodyPublishers.ofString(postRequestBody))
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return room;
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return null;
        }

        return gson.fromJson(response.body(), Room.class);
    }


    /** Sends a PUT request to the server to make a room inactive.
     * @param code the room link as a String
     */
    public static void updateRoom(String code) {
        code = code.substring(28);
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

    /** Updates attribute "text" of question corresponding to this id in database.
     * Makes PUT request to server. (QuestionController - QuestionService)
     * @param questionId - id of question to be updated in database
     * @return boolean - true if PUT operation succeeded, false otherwise.
     */
    public static boolean editQuestion(long questionId, String update) {

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
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Something went wrong!");
            error.show();
            return false;
        }
        return true;
    }


    /** Makes POST request to server, to store new question in database.
     * Server will attach an id to this question (generated by db)
     * Handled by QuestionController.
     * @param newQuestion - new Question to be posted
     * @return Long - generated id for this question
     */
    public static Long postQuestion(Question newQuestion) {

        if (newQuestion == null) {
            return (long)-1;
        }

        // not the best way to do it (goes wrong if someone adds ", " in one of the fields
        String postRequestBody = newQuestion.getRoomId() + ", "
                + newQuestion.getText() + ", " + newQuestion.getOwner();

        // send request to the server
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/questions"))
                .POST(HttpRequest.BodyPublishers.ofString(postRequestBody))
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return (long)-1;
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return (long)-1;
        }

        return gson.fromJson(String.valueOf(Long.parseLong(response.body())), Long.class);
    }
}
