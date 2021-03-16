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
import java.util.List;

import com.google.gson.reflect.TypeToken;
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

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/rooms/" + code)).build();
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

    public static List<Question> getQuestions(Room room) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/questions/" + room))
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            // not sure? maybe return something else
            return null;
        }

        return gson.fromJson(response.body(), new TypeToken<List<Question>>(){}.getType());
    }

    /**
     * Retrieves a list of all answered questions
     * from the server for a specific room
     * @param room room where we want to retrieve the questions from
     * @return the body of a get request to the server (list of questions).
     */
    public static List<Question> getAnsweredQuestions(Room room) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/questions/answered/" + room)).build();
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
