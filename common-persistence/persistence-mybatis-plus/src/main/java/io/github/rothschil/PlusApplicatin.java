package io.github.rothschil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("io.github.rothschil.**.mapper")
@SpringBootApplication
public class PlusApplicatin {

    public static void main(String[] args) {
        SpringApplication.run(PlusApplicatin.class,args);
    }
}
