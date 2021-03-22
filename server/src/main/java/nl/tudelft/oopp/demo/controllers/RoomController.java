package nl.tudelft.oopp.demo.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
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
        return roomService.getRooms();
    }

    @GetMapping("example")   // http://localhost:8080/rooms/example
    @ResponseBody
    public Room getExampleRoom() {
        return roomService.getRoomById(4);
    }

    @GetMapping("/{roomCode}")  // http://localhost:8080/rooms/{roomCode}
    @ResponseBody
    public Room getRoomByCode(@PathVariable String roomCode) {
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
        roomService.updateRoomStatusByLink(url);
    }

    @PostMapping   // http://localhost:8080/rooms
    public Room addNewRoom(@RequestBody String data) throws MalformedURLException {
        return roomService.addNewRoom(data);
    }

    @PutMapping("/{roomCode}/{feedback}") // http://localhost:8080/rooms/{roomCode}/{feedback}
    public void updateFeedback(@PathVariable String roomCode, @PathVariable String feedback) {
        roomService.updateRoomSpeed("http://localhost:8080/rooms/" + roomCode, feedback);
    }

    @GetMapping("{roomCode}")
    public List<User> getParticipants(@PathVariable String roomCode) {
        return roomService.getRoomByCode("http://localhost:8080/rooms" + roomCode).getParticipants();
    }
}
