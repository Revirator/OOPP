package nl.tudelft.oopp.demo.config;

import nl.tudelft.oopp.demo.DemoApplication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class LoggerConfig {
    public static void logRequest(String text) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ip = request.getRemoteAddr();
        DemoApplication.logger.info(ip + " " + text);
    }
}
