package nl.tudelft.oopp.demo.controllers;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping // http://localhost:8080/rooms : that shouldn't return anything
    public List<Room> getRooms() {
        return null;
    }

    @GetMapping("example")   // http://localhost:8080/rooms/example
    @ResponseBody
    public Room getExampleRoom() throws MalformedURLException {
        return new Room(1, LocalDateTime.now(), "Example Room");    // Used for testing
    }

    @GetMapping("/{roomCode}")  // http://localhost:8080/rooms/{roomCode}
    @ResponseBody
    public Room getRoomByCode(@PathVariable String roomCode) {
        return roomService.getRoomByCode("http://localhost:8080/rooms/" + roomCode);
    }

    @PostMapping   // http://localhost:8080/rooms
    public void addNewRoom(@RequestBody Room room) {
        roomService.addNewRoom(room);
    }
}
