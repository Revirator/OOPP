package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getRooms() { return roomService.getRooms(); }

    @GetMapping(path = "{roomId}")  // http://localhost:8080/rooms/{roomId}
    public Room getRoomById(@PathVariable("roomId") Long roomId)
    {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("example")   // http://localhost:8080/rooms/example
    @ResponseBody
    public Room getExampleRoom() throws MalformedURLException {
        return new Room(1, LocalDateTime.now(), "Example Room");
    }

}
