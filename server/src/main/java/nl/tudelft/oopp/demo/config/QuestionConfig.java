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
    CommandLineRunner questionCommandLineRunner(QuestionRepository questionRepository,
                                         RoomRepository roomRepository) {
        return args -> {

            Room calcy1 = new Room(
                    LocalDateTime.of(2021, Month.MARCH, 19, 8, 45, 00),
                    "Calculus", true);

            Room adsy1 = new Room(
                    LocalDateTime.of(2021, Month.JANUARY, 8, 11, 45, 00),
                    "Algorithms and Datastructures", true);

            roomRepository.save(calcy1);
            roomRepository.save(adsy1);

            Question senne = new Question(
                    1,
                    adsy1,
                    "How do we get a remote psql database?",
                    "Senne", 12
            );

            Question pavel = new Question(
                    2,
                    adsy1,
                    "What is the meaning of life?",
                    "Pavel", 99
            );

            Question bora = new Question(
                    3,
                    adsy1,
                    "What has to be included in our presentation?",
                    "Bora", 1
            );

            Question emke = new Question(
                    4,
                    calcy1,
                    "Why is the invertible matrix theorem so long?",
                    "Emke", 12
            );

            Question nadine = new Question(
                    5,
                    calcy1,
                    "What is the basis of the zero subspace?",
                    "Nadine", 0
            );

            Question denis = new Question(
                    6,
                    calcy1,
                    "When are the grades out??",
                    "Denis", 87
            );

            questionRepository.saveAll(List.of(senne, pavel, bora, emke, nadine, denis));
        };
    }


}
