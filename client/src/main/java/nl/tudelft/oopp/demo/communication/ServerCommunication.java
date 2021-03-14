package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.data.Room;

public class ServerCommunication {

    private static final HttpClient client = HttpClient.newBuilder().build();

    // Had to modify the serializer because the room entity uses LocalDateTime
    // and for some reason gson doesn't support that...
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();

    /** Retrieves a room from the server.
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

    /** Sends room to the server, returns a room with URLs.
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

    /** Sends feedback to the server which is processed and the rooms are updated.
     * @param url the students link connected to a room
     * @param feedback the feedback we want to send
     */
    public static void sendFeedback(URL url, String feedback) {
        String roomCode = url.toString().substring(28);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/rooms/" + roomCode + "/" + feedback))
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
}
