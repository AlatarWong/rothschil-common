package io.github.rothschil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/6 - 21:27
 * @since 1.0.0
 */
@EnableAsync
@SpringBootApplication
public class LiquibaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiquibaseApplication.class, args);
    }
}
