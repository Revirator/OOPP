package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.LoggerConfig.logRequest;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("students/{roomId}")   // http://localhost:8080/users/students/{roomId}
    public List<Student> getStudents(@PathVariable("roomId") Long roomId) {
        logRequest("to get all students for the room with an id '" + roomId + "'");
        return userService.getStudents(roomId);
    }

    @GetMapping("moderators/{roomId}")   // http://localhost:8080/users/moderators/{roomId}
    public List<Moderator> getModerators(@PathVariable("roomId") Long roomId) {
        logRequest("to get all moderators for the room with an id '" + roomId + "'");
        return userService.getModerators(roomId);
    }
}
