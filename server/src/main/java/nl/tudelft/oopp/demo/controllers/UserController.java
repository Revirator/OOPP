package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;


    /**
     * Autowired constructor for the class.
     * @param userService userService
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * GET mapping.
     * @param roomId the id of the required room
     * @return all students for a specific room
     */
    @GetMapping("students/{roomId}")   // http://localhost:8080/users/students/{roomId}
    @ResponseBody
    public List<Student> getStudents(@PathVariable("roomId") Long roomId) {
        return userService.getStudents(roomId);
    }


    /**
     * GET mapping.
     * @param roomId the id of the required room
     * @return all moderators for a specific room
     */
    @GetMapping("moderators/{roomId}")   // http://localhost:8080/users/moderators/{roomId}
    @ResponseBody
    public List<Moderator> getModerators(@PathVariable("roomId") Long roomId) {
        return userService.getModerators(roomId);
    }


    /**
     * GET mapping.
     * @param studentId the id of the required student
     * @return a student with a specific id
     */
    @GetMapping("/{studentId}") //http://localhost:8080/users/{studentId}
    @ResponseBody
    public Optional<Student> getStudent(@PathVariable Long studentId) {
        return userService.getStudentById(studentId);
    }


    /**
     * POST mapping, adds a new student to a room.
     * @param roomId the id of the room
     * @param nickname the nickname of the new student
     * @return id of the new student
     */
    @PostMapping("/addUser/Student/{roomId}/{nickname}") // http://localhost:8080/users/addUser/Student/{roomId}/{nickname}
    public Long addStudent(@PathVariable long roomId, @PathVariable String nickname) {
        return userService.addStudent(nickname,roomId);
    }


    /**
     * POST mapping, adds a new moderator to a room.
     * @param roomId the id of the room
     * @param nickname the nickname of the new moderator
     * @return id of the new moderator
     */
    @PostMapping("/addUser/Moderator/{roomId}/{nickname}") // http://localhost:8080/users/addUser/Moderator/{roomId}/{nickname}
    public Long addModerator(@PathVariable long roomId, @PathVariable String nickname) {
        return userService.addModerator(nickname,roomId);
    }
}
