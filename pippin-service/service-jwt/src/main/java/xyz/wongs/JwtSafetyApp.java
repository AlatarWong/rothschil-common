package xyz.wongs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @Description TODO
 * 
 * @date
 * @Version 1.0.0
 */
@SpringBootApplication
public class JwtSafetyApp {
    static final Logger LOGGER = LoggerFactory.getLogger(JwtSafetyApp.class);
    public static void main(String[] args) {
        try {
            SpringApplication.run(JwtSafetyApp.class, args);
        } catch (Throwable e) {
            LOGGER.error("System error", e);
        }
    }
}
