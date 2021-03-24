package nl.tudelft.oopp.demo.communication;

import com.google.gson.reflect.TypeToken;
import nl.tudelft.oopp.demo.data.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GetServerCommunication extends ServerCommunication{

    /** Retrieves a room from the server.
     * @param code room identification code
     * @return the body of the response from the server or null if the room does not exist.
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
     * Fetches a list of all students.
     * @param roomID ID of the room
     * @return a list of all students in the room
     */
    public static List<Student> getStudents(long roomID) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/rooms/students/" + roomID))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return List.of();
        }
        return gson.fromJson(response.body(), new TypeToken<List<Student>>() {
        }.getType());
    }

    /** Sends an id to the server.
     * The server return the student with the given id ..
     * .. or null if the student doesn't exist.
     * @param studentId the id of the student to be updated
     * @return User - a new instance of Student corresponding to the id
     */
    public static User getStudent(Long studentId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/users/" + studentId))
                .GET().build();
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
        return gson.fromJson(response.body(), Student.class);
    }

    /**
     * Fetches a list of all moderators.
     * @param roomID ID of the room
     * @return a list of all moderators in the room
     */
    public static List<Moderator> getModerators(long roomID) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/rooms/moderators/" + roomID))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return List.of();
        }
        return gson.fromJson(response.body(), new TypeToken<List<Moderator>>(){}.getType());
    }

    /**
     * Retrieves a list of all questions.
     * @param roomID that we want the questions from
     * @return list of all questions in that room
     */
    public static List<Question> getQuestions(long roomID) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/questions/" + roomID))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            // not sure? maybe return something else
            return null;
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return List.of();
        }

        return gson.fromJson(response.body(), new TypeToken<List<Question>>(){}.getType());
    }

    /**
     * Retrieves a list of all answered questions.
     * from the server for a specific room
     * @param roomID room identification code
     * @return the body of a get request to the server (list of questions).
     */
    public static List<Question> getAnsweredQuestions(long roomID) {

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/questions/answered/" + roomID))
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return List.of();     // Not sure if that is needed but leaving it anyways
        }

        return gson.fromJson(response.body(), new TypeToken<List<Question>>(){}.getType());
    }
}
