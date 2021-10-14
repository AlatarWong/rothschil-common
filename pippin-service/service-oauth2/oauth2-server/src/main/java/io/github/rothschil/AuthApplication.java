package io.github.rothschil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 11:49
 * @since 1.0.0
 */
@EnableCaching
@MapperScan(basePackages = {"io.github.rothschil.**.mapper"})
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}
