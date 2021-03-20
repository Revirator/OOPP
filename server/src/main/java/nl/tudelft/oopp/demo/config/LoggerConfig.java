package nl.tudelft.oopp.demo.config;

import nl.tudelft.oopp.demo.DemoApplication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Scanner;

public class LoggerConfig {
    public static void logRequest(String requested) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ip = request.getRemoteAddr();
        DemoApplication.logger.info(ip + " requested " + requested + ".");
    }

    public static int getFirstNumber(String text) {
        int num = new Scanner(text).useDelimiter(", ").nextInt();
        return num;
    }
}
