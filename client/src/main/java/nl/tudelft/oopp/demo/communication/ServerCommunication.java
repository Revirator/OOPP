package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.data.Moderator;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.Student;
import nl.tudelft.oopp.demo.data.User;

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

    /** Sends a user to the server, who is saved in the DB ..
     * .. and added to the list of participants in the ..
     * .. corresponding room instance.
     * @param user the user to be saved in the DB
     * @param roomId the id of the room the user is a participant in
     * @return Long - the id of the user
     */
    public static Long sendUser(User user, long roomId) {
        String requestUrl = "http://localhost:8080/users/addUser/" + user.getRole()
                +  "/" + roomId + "/" + user.getNickname();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return (long) - 1;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return (long) - 1;
        }
        return gson.fromJson(String.valueOf(Long.parseLong(response.body())), Long.class);
    }

    //    /**
    //     * Fetches a list of all participants.
    //     * @param roomID ID of the room
    //     * @return a list of all participants in the room
    //     */
    //    public static List<User> getParticipants(long roomID) {
    //        HttpRequest request = HttpRequest.newBuilder()
    //                .GET()
    //                .uri(URI.create("http://localhost:8080/rooms/participants/" + roomID))
    //                .build();
    //        HttpResponse<String> response;
    //
    //        try {
    //            response = client.send(request, HttpResponse.BodyHandlers.ofString());
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            return null;
    //        }
    //
    //        if (response.statusCode() != 200) {
    //            System.out.println("Status: " + response.statusCode());
    //            return List.of();
    //        }
    //
    //        return gson.fromJson(response.body(), new TypeToken<List<User>>(){}.getType());
    //    }


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
            return ;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
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
     * @param roomId room identification code
     * @return the body of a get request to the server (list of questions).
     */
    public static List<Question> getAnsweredQuestions(long roomId) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/questions/answered/" + roomId)).build();
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

        // not the best way to do it (goes wrong if someone adds "& " in one of the fields
        String postRequestBody = newQuestion.getRoom() + "& "
                 + newQuestion.getOwner() + "& " + newQuestion.getText();

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
}
