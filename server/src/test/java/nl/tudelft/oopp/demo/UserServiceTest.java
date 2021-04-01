package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.services.QuestionService;
import nl.tudelft.oopp.demo.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class) // automatically closes repo after each test
public class UserServiceTest {


    // We are mocking the repositories, since these are tested in isolation.
    @Mock
    private UserRepository<Student> studentUserRepository;
    @Mock
    private UserRepository<Moderator> moderatorUserRepository;
    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserService userService;
    @Mock
    private Student student1;
    @Mock
    private Moderator moderator1;
    @Mock
    private Room roomOne;

    @BeforeEach
    void setUp() {
        userService = new UserService(studentUserRepository,
                moderatorUserRepository, roomRepository);
    }


    /** Constructor for this test class.
     * Creates an example room in which test Users are.
     */
    public UserServiceTest() {
        this.roomOne = new Room(
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 00),
                "Software Quality And Testing", true);
        student1 = new Student(1, "Pim", roomOne);
        moderator1 = new Moderator(2, "Stefan", roomOne);
    }


    @Test
    public void testGetStudentById() {
        Optional<Student> output = userService.getStudentById((long)0);
        assertNotNull(output);
        verify(studentUserRepository).findById(any());
    }

    @Test
    public void testAddStudent() {
        String payload = "Pim, 127.0.0.1, 1";
        given(roomRepository.getOne(any())).willReturn(roomOne);
        given(studentUserRepository.save(any())).willReturn(student1);

        userService.addStudent(payload);

        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(roomRepository).getOne(any());
        verify(studentUserRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertEquals("Pim", capturedStudent.getNickname());
    }


    @Test
    public void testAddModerator() {
        String payload = "Stefan, 2";
        given(roomRepository.getOne(any())).willReturn(roomOne);
        given(moderatorUserRepository.save(any())).willReturn(moderator1);

        userService.addModerator(payload);

        ArgumentCaptor<Moderator> moderatorArgumentCaptor =
                ArgumentCaptor.forClass(Moderator.class);

        verify(roomRepository).getOne(any());
        verify(moderatorUserRepository).save(moderatorArgumentCaptor.capture());

        Moderator capturedModerator = moderatorArgumentCaptor.getValue();
        assertEquals("Stefan", capturedModerator.getNickname());
    }



    @Test
    public void testBanStudent() {
        given(studentUserRepository.findById(1)).
                willReturn(student1);
        userService.banStudent(1);
        verify(student1).ban();
    }

    @Test
    public void testCheckIfBanned() {
        given(roomRepository.getOne(any())).willReturn(roomOne);
        userService.checkIfBanned(1, "127.0.0.1, 1");
        verify(roomRepository).getOne(any());
        verify(roomOne).getStudents();
    }

    @Test
    public void testRemoveStudent() {
        given(studentUserRepository.getOne(any())).willReturn(student1);
        assertTrue(userService.removeUser(1));
        verify(studentUserRepository).deleteById(any());
    }


    @Test
    public void testRemoveModerator() {
        given(moderatorUserRepository.getOne(any())).willReturn(moderator1);
        assertTrue(userService.removeUser(2));
        verify(moderatorUserRepository).deleteById(any());
    }


    @Test
    public void testDeleteNull() {
        assertFalse(userService.removeUser(0));
    }

}
