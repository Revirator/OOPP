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
    @GetMapping("/{studentId}")
    @ResponseBody
    public Optional<Student> getStudent(@PathVariable long studentId) {
        return userService.getStudentById(studentId);
    }

    /**
     * POST mapping, adds a new student to a room.
     * @param data the JSON of a Student object to be added to the DB
     * @return id of the new student
     */
    @PostMapping("/addUser/Student")
    @ResponseBody
    public Long addStudent(@RequestBody String data) {
        return userService.addStudent(data);
    }

    /**
     * POST mapping, adds a new moderator to a room.
     * @param data the JSON of a Moderator object to be added to the DB
     * @return id of the new moderator
     */
    @PostMapping("/addUser/Moderator")
    @ResponseBody
    public Long addModerator(@RequestBody String data) {
        return userService.addModerator(data);
    }

    /**
     * PUT mapping, updates the banned field of a student to true.
     * @param studentId the ID of the student that is getting banned
     */
    @PutMapping("/ban/{studentId}")
    public void banStudent(@PathVariable long studentId) {
        userService.banStudent(studentId);
    }

    /**
     * GET mapping, checks if a student with the same IP address has already be banned.
     * @param roomId the ID of the room for which we are making the check
     * @param ipAddress the IP address of the student
     * @return true if the IP is linked to a student that has been banned and false otherwise
     */
    @GetMapping("/isBanned/{roomId}/{ipAddress}")
    @ResponseBody
    public boolean checkIfBanned(@PathVariable long roomId, @PathVariable String ipAddress) {
        return userService.checkIfBanned(roomId, ipAddress);
    }

    /**
     * DELETE mapping, removes a user from the DB.
     * @param userId the ID of the user
     * @return true if the user has been removed successfully and false otherwise
     */
    @DeleteMapping("/remove/{userId}")
    @ResponseBody
    public boolean removeUser(@PathVariable long userId) {
        return userService.removeUser(userId);
    }
}
