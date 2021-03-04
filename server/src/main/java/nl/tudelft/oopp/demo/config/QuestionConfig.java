package nl.tudelft.oopp.demo.config;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QuestionConfig {

    @Bean
    CommandLineRunner commandLineRunner2(QuestionRepository repository, RoomRepository repository2) {
        return args -> {

            Room ooppy1 = new Room(
                    LocalDateTime.of(2021, Month.APRIL, 19, 12, 45, 00),
                    "OOP Project");

            repository2.save(ooppy1);

            Question senne = new Question(
                    1,
                    ooppy1,
                    "How do we get a remote psql database?",
                    "Senne", 12
            );

            Question pavel = new Question(
                    2,
                    ooppy1,
                    "What is the meaning of life?",
                    "Pavel", 99
            );

            Question bora = new Question(
                    3,
                    ooppy1,
                    "What has to be included in our presentation?",
                    "Bora", 1
            );

            Question emke = new Question(
                    4,
                    ooppy1,
                    "Why is the invertible matrix theorem so long?",
                    "Emke", 12
            );

            Question nadine = new Question(
                    5,
                    ooppy1,
                    "What is the basis of the zero subspace?",
                    "Nadine", 0
            );

            Question denis = new Question(
                    6,
                    ooppy1,
                    "When are the grades out??",
                    "Denis", 87
            );

            repository.saveAll(List.of(senne, pavel, bora, emke, nadine, denis));
        };
    }


}
