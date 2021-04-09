package nl.tudelft.oopp.demo.config;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Moderator;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Student;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner userCommandLineRunner(UserRepository<Student> studentUserRepository,
                                            UserRepository<Moderator> moderatorUserRepository,
                                            RoomRepository roomRepository) {
        return args -> {

            Room pts = new Room(
                    LocalDateTime.of(2021, Month.MARCH, 15, 8, 45, 0),
                    "Probability Theory and Statistics", true);

            roomRepository.save(pts);

            String ipAddress = InetAddress.getLocalHost().getHostAddress();

            Student senne = new Student("Senne", pts, ipAddress);
            Student pavel = new Student("Pavel", pts, ipAddress);
            Student bora = new Student("Bora", pts, ipAddress);
            Student emke = new Student("Emke", pts, ipAddress);
            Student nadine = new Student("Nadine", pts, ipAddress);
            Student denis = new Student("Denis", pts, ipAddress);

            studentUserRepository.saveAll(List.of(senne, pavel, bora, emke, nadine, denis));

            Moderator bert = new Moderator("Bert", pts);
            Moderator henk = new Moderator("Henk", pts);

            moderatorUserRepository.saveAll(List.of(bert, henk));
        };
    }




}
