package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
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

    public Optional<Student> getStudentById(Long studentId) {
        return studentUserRepository.findById(studentId);
    }

    /** Adds the student to the DB.
     * @param nickname the nickname of the student
     * @param roomId the id of the room the student is in
     * @return the id of the student
     */
    public Long addStudent(String nickname, long roomId) {
        Room room = roomRepository.getOne(roomId);
        Student student = new Student(nickname,room);
        studentUserRepository.save(student);
        room.addParticipant(student);
        return student.getId();
    }

    /** Adds the moderator to the DB.
     * @param nickname the nickname of the moderator
     * @param roomId the id of the room the student is in
     * @return the id of the moderator
     */
    public Long addModerator(String nickname, long roomId) {
        Room room = roomRepository.getOne(roomId);
        Moderator moderator = new Moderator(nickname,room);
        moderatorUserRepository.save(moderator);
        room.addParticipant(moderator);
        return moderator.getId();
    }

    /** Updates the banned field of the student with the corresponding id.
     * @param studentId the id of the student
     */
    @Transactional
    public void banStudent(long studentId) {
        Student student = studentUserRepository.findById(studentId);
        if (student != null) {
            student.ban();
        }
    }

    /** Checks if the IP address of the student is the same as ..
     * .. the IP address of an already banned student.
     * @param roomId the id of the room
     * @param ipAddress the IP address of the student
     * @return true if the user is banned, false otherwise
     */
    public boolean checkIfBanned(long roomId, String ipAddress) {
        List<Student> studentList = roomRepository.getOne(roomId).getStudents().stream()
                .filter(s -> s.isBanned()).collect(Collectors.toList());
        List<String> ipAddresses = studentList.stream()
                .map(s -> s.getIpAddress()).collect(Collectors.toList());
        return ipAddresses.contains(ipAddress);
    }
}
