package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /** Finds Student by id.
     * @param studentId - long
     * @return Optional of Student having this id
     */
    public Optional<Student> getStudentById(Long studentId) {
        return studentUserRepository.findById(studentId);
    }

    /** Adds the student to the DB.
     * @param data the JSON of a Student object to be added to the DB
     * @return the id of the student
     */
    public Long addStudent(String data) {
        String[] dataArray = data.split(", ");

        String studentName = dataArray[0];
        String ipAddress = dataArray[1];
        Room room = roomRepository.getOne(Long.parseLong(dataArray[2]));
        return studentUserRepository.save(new Student(studentName,room,ipAddress)).getId();
    }

    /** Adds the moderator to the DB.
     * @param data the JSON of a Moderator object to be added to the DB
     * @return the id of the moderator
     */
    public Long addModerator(String data) {
        String[] dataArray = data.split(", ");

        String moderatorName = dataArray[0];
        Room room = roomRepository.getOne(Long.parseLong(dataArray[1]));
        return moderatorUserRepository.save(new Moderator(moderatorName,room)).getId();
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

    /** Checks if the ID is linked to an existing Student/Moderator ..
     * .. and if it is, it is removed from the respective DB and ..
     * .. the method returns true.
     * If the ID is not in the DB the method returns false.
     */
    @Transactional
    public boolean removeUser(long userId) {
        User userToBeRemoved = studentUserRepository.getOne(userId);
        if (userToBeRemoved == null) {
            userToBeRemoved = moderatorUserRepository.getOne(userId);
            if (userToBeRemoved == null) {
                return false;
            }
            moderatorUserRepository.deleteById(userId);
            return true;
        }
        studentUserRepository.deleteById(userId);
        return true;
    }
}
