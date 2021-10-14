package io.github.rothschil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:03
 * @since 1.0.0
 */
@EnableCaching
@MapperScan(basePackages = {"xyz.wongs.**.mapper"})
@SpringBootApplication
public class AreaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AreaApplication.class, args);
    }

}
