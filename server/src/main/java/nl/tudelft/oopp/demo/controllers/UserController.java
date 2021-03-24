package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.LoggerConfig.logRequest;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("students/{roomId}")   // http://localhost:8080/users/students/{roomId}
    @ResponseBody
    public List<Student> getStudents(@PathVariable("roomId") Long roomId) {
        logRequest("to get all students for the room with an id '" + roomId + "'");
        return userService.getStudents(roomId);
    }

    @GetMapping("moderators/{roomId}")   // http://localhost:8080/users/moderators/{roomId}
    @ResponseBody
    public List<Moderator> getModerators(@PathVariable("roomId") Long roomId) {
        logRequest("to get all moderators for the room with an id '" + roomId + "'");
        return userService.getModerators(roomId);
    }

    @GetMapping("/{studentId}") //http://localhost:8080/users/{studentId}
    @ResponseBody
    public Optional<Student> getStudent(@PathVariable Long studentId) {
        return userService.getStudentById(studentId);
    }

    @PostMapping("/addUser/Student/{roomId}/{nickname}") // http://localhost:8080/users/addUser/Student/{roomId}/{nickname}
    public Long addStudent(@PathVariable long roomId, @PathVariable String nickname) {
        return userService.addStudent(nickname,roomId);
    }

    @PostMapping("/addUser/Moderator/{roomId}/{nickname}") // http://localhost:8080/users/addUser/Moderator/{roomId}/{nickname}
    public Long addModerator(@PathVariable long roomId, @PathVariable String nickname) {
        return userService.addModerator(nickname,roomId);
    }

    @PutMapping("/ban/{studentId}") // http://localhost:8080/users/ban/{studentId}
    public void banStudent(@PathVariable Long studentId) {
        userService.banStudent(studentId);
    }
}
