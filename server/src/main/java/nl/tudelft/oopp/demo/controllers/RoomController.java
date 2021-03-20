package nl.tudelft.oopp.demo.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static nl.tudelft.oopp.demo.config.LoggerConfig.logRequest;

@RestController
@RequestMapping("rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping // http://localhost:8080/rooms
    public List<Room> getRooms() {
        logRequest("to get all rooms from the database");
        return roomService.getRooms();
    }

    @GetMapping("example")   // http://localhost:8080/rooms/example
    @ResponseBody
    public Room getExampleRoom() {
        logRequest("to get the example room");
        return roomService.getRoomById(4);
    }

    @GetMapping("/{roomCode}")  // http://localhost:8080/rooms/{roomCode}
    @ResponseBody
    public Room getRoomByCode(@PathVariable String roomCode) {
        logRequest("to join room with code '" + roomCode + "'");
        return roomService.getRoomByCode("http://localhost:8080/rooms/" + roomCode);
    }

    /** Updates the status of the room from active to inactive.
     * @param roomCode the link connected to the room
     */
    @PutMapping("/update/{roomCode}") // http://localhost:8080/rooms/update/{roomCode}
    public void updateRoom(@PathVariable String roomCode) {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/rooms/" + roomCode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        logRequest("to deactivate the room with code '" + roomCode + "'");
        roomService.updateRoomStatusByLink(url);
    }

    @PostMapping   // http://localhost:8080/rooms
    public Room addNewRoom(@RequestBody String data) throws MalformedURLException {
        logRequest("to create a new room");
        return roomService.addNewRoom(data);
    }

    @PutMapping("/{roomCode}/{feedback}") // http://localhost:8080/rooms/{roomCode}/{feedback}
    public void updateFeedback(@PathVariable String roomCode, @PathVariable String feedback) {
        DemoApplication.logger.info("Updated the feedback for room with a code '" + roomCode + "'");
        roomService.updateRoomSpeed("http://localhost:8080/rooms/" + roomCode, feedback);
    }
}
