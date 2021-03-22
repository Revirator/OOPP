package nl.tudelft.oopp.demo.services;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final RoomRepository roomRepository;
    private final UserRepository<Student> studentUserRepository;
    private final UserRepository<Moderator> moderatorUserRepository;


    /** Constructor for UserService.
     * @param studentUserRepository - retrieves Students from database.
     * @param moderatorUserRepository - retrieves Moderators from database.
     * @param roomRepository - retrieves Rooms from database.
     */
    @Autowired
    public UserService(UserRepository<Student> studentUserRepository,
                           UserRepository<Moderator> moderatorUserRepository,
                           RoomRepository roomRepository) {
        this.studentUserRepository = studentUserRepository;
        this.moderatorUserRepository = moderatorUserRepository;
        this.roomRepository = roomRepository;
    }


    /** Called by UserController.
     * @return a List of students.
     *          Example:
     *          GET http://localhost:8080/users/students/{roomId}
     */
    public List<Student> getStudents(long roomId) {
        return studentUserRepository.findAllByRoomRoomId(roomId);
    }

    /** Called by UserController.
     * @return a List of moderators.
     *          Example:
     *          GET http://localhost:8080/users/moderators/{roomId}
     */
    public List<Moderator> getModerators(long roomId) {
        return moderatorUserRepository.findAllByRoomRoomId(roomId);
    }

    public void addStudent(String nickname, long roomId) {
        Room room = roomRepository.getOne(roomId);
        User student = new Student(nickname,room);
        studentUserRepository.save((Student) student);
    }

    public void addModerator(String nickname, long roomId) {
        Room room = roomRepository.getOne(roomId);
        User moderator = new Moderator(nickname,room);
        moderatorUserRepository.save((Moderator) moderator);
    }
}
