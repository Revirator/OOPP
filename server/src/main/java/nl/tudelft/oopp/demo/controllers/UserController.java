package nl.tudelft.oopp.demo.controllers;

import java.util.List;

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
        return userService.getStudents(roomId);
    }

    @GetMapping("moderators/{roomId}")   // http://localhost:8080/users/moderators/{roomId}
    @ResponseBody
    public List<Moderator> getModerators(@PathVariable("roomId") Long roomId) {
        return userService.getModerators(roomId);
    }

    @PostMapping("/addUser/Student/{roomId}/{nickname}") // http://localhost:8080/users/addUser/Student/{roomId}/{nickname}
    public void addStudent(@PathVariable long roomId, @PathVariable String nickname) {
        userService.addStudent(nickname,roomId);
    }

    @PostMapping("/addUser/Moderator/{roomId}/{nickname}")
    public void addModerator(@PathVariable long roomId, @PathVariable String nickname) {
        userService.addModerator(nickname,roomId);
    }
}
