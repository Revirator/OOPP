package nl.tudelft.oopp.demo.config;

import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.DemoApplication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LoggerConfig {

    /**
     * Logs the inserted text to spring.log and console.
     * Also adds the ip of the reqester
     * @param requested the text that should be logged
     */
    public static void logRequest(String requested) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ip = request.getRemoteAddr();
        DemoApplication.logger.info(ip + " requested " + requested + ".");
    }

    /**
     * Gets the first integer from a string,
     * used to get ids from http payloads.
     * @param text some string with delimiter "& "
     * @return the first integer in the string
     */
    public static int getFirstNumber(String text) {
        int num = new Scanner(text).useDelimiter("& ").nextInt();
        return num;
    }
}
