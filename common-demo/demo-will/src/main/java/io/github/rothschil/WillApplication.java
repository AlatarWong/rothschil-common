package io.github.rothschil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/6 - 21:27
 * @since 1.0.0
 */
@MapperScan("io.github.rothschil.**.mapper")
@SpringBootApplication
public class WillApplication {

    public static void main(String[] args) {
        SpringApplication.run(WillApplication.class, args);
    }
}
