package nl.tudelft.oopp.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    /**
     * Main method of the server application.
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        logger.info("Server started.");
    }
}
