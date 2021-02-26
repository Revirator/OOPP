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
                    "How do we get a remote psql database?",
                    "Senne",
                    LocalDateTime.now(),
                    19
            );

            Question pavel = new Question(
                    2,
                    "What is the meaning of life?",
                    "Pavel",
                    LocalDateTime.now()
                    , 99
            );

            Question bora = new Question(
                    3,
                    "What has to be included in our presentation?",
                    "Bora",
                    LocalDateTime.now()
                    , 4
            );

            Question emke = new Question(
                    4,
                    "Why is the invertible matrix theorem so long?",
                    "Emke",
                    LocalDateTime.now()
                    , 19
            );

            Question nadine = new Question(
                    5,
                    "What is the basis of the zero subspace?",
                    "Nadine",
                    LocalDateTime.now()
                    , 0
            );

            Question denis = new Question(
                    6,
                    "When are the grades out??",
                    "Denis",
                    LocalDateTime.now()
                    , 98
            );

            repository.saveAll(List.of(senne, pavel, bora, emke, nadine, denis));
        };
    }


}
