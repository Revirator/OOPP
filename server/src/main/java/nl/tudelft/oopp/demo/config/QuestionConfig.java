package nl.tudelft.oopp.demo.config;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Configuration
public class QuestionConfig {

    @Bean
    CommandLineRunner commandLineRunner(QuestionRepository repository) {
        return args -> {

            Question senne = new Question(
                    1,
                    "ROOM1",
                    "How do we get a remote psql database?",
                    "Senne", 12
            );

            Question pavel = new Question(
                    2,
                    "ROOM1",
                    "What is the meaning of life?",
                    "Pavel", 99
            );

            Question bora = new Question(
                    3,
                    "ROOM1",
                    "What has to be included in our presentation?",
                    "Bora", 1
            );

            Question emke = new Question(
                    4,
                    "ROOM2",
                    "Why is the invertible matrix theorem so long?",
                    "Emke", 12
            );

            Question nadine = new Question(
                    5,
                    "ROOM2",
                    "What is the basis of the zero subspace?",
                    "Nadine", 0
            );

            Question denis = new Question(
                    6,
                    "ROOM2",
                    "When are the grades out??",
                    "Denis", 87
            );

            repository.saveAll(List.of(senne, pavel, bora, emke, nadine, denis));
        };
    }


}
