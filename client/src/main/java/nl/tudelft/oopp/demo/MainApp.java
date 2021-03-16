package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.views.SplashView;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class MainApp {
    public static void main(String[] args) {
        SplashView.main(new String[0]);
    }
}
