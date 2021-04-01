package nl.tudelft.oopp.demo.controllers;

import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * @param data the JSON of a Student object to be added to the DB
     * @return id of the new student
     */
    @PostMapping("/addUser/Student") // http://localhost:8080/users/addUser/Student
    public Long addStudent(@RequestBody String data) {
        return userService.addStudent(data);
    }

    /**
     * POST mapping, adds a new moderator to a room.
     * @param data the JSON of a Moderator object to be added to the DB
     * @return id of the new moderator
     */
    @PostMapping("/addUser/Moderator") // http://localhost:8080/users/addUser/Moderator
    public Long addModerator(@RequestBody String data) {
        return userService.addModerator(data);
    }


    @PutMapping("/ban/{studentId}") // http://localhost:8080/users/ban/{studentId}
    public void banStudent(@PathVariable Long studentId) {
        userService.banStudent(studentId);
    }



    @GetMapping("/isBanned/{roomId}/{ipAddress}") // http://localhost:8080/users/isBanned/{roomId}/{ipAddress}
    public boolean checkIfBanned(@PathVariable Long roomId, @PathVariable String ipAddress) {
        return userService.checkIfBanned(roomId, ipAddress);
    }


    @DeleteMapping("/remove/{userId}") // http://localhost:8080/users/remove/{userId}
    public boolean removeUser(@PathVariable long userId) {
        return userService.removeUser(userId);
    }
}
