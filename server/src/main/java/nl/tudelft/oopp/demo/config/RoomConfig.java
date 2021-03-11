package nl.tudelft.oopp.demo.config;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RoomConfig {

    @Bean
    CommandLineRunner commandLineRunner(RoomRepository repository) {
        return args -> {
            // id will be generated by DB!
            Room lay1 = new Room(
                    LocalDateTime.of(2020, Month.APRIL, 16, 8, 45, 00),
                    "Linear Algebra");

            Room idmy1 = new Room(
                    LocalDateTime.of(2021, Month.MARCH, 9, 13, 30, 00),
                    "Information and Database Management");

            Room ooppy1 = new Room(
                    LocalDateTime.of(2021, Month.APRIL, 17, 12, 45, 00),
                    "OOP Project");

            repository.saveAll(List.of(lay1, idmy1, ooppy1));
        };
    }

}
