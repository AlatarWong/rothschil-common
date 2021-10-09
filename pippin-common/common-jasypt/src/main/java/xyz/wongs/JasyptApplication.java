package xyz.wongs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 14:41
 * @since 1.0.0
 */
@SpringBootApplication
@EnableAsync
public class JasyptApplication {
    public static void main(String[] args) {
        SpringApplication.run(JasyptApplication.class, args);
    }
}
