package nl.tudelft.oopp.demo.config;

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
                    LocalDateTime.of(2021, Month.JUNE, 15, 8, 45, 00),
                    "Probability Theory and Statistics", true);

            roomRepository.save(pts);

            Student senne = new Student("Senne", pts);
            Student pavel = new Student("Pavel", pts);
            Student bora = new Student("Bora", pts);
            Student emke = new Student("Emke", pts);
            Student nadine = new Student("Nadine", pts);
            Student denis = new Student("Denis", pts);

            studentUserRepository.saveAll(List.of(senne, pavel, bora, emke, nadine, denis));

            Moderator bert = new Moderator("Bert", pts);
            Moderator henk = new Moderator("Henk", pts);

            moderatorUserRepository.saveAll(List.of(bert, henk));
        };
    }




}
