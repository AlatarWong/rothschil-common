package xyz.wongs.drunkard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @author WCNGS@QQ.COM
 * @date 2020/8/2 13:39
 * @since 1.0.0
*/
@ServletComponentScan
@EnableAsync
@MapperScan(basePackages = {"xyz.wongs.drunkard.**.mapper"})
//@ComponentScan(basePackages = {"xyz.wongs.drunkard"})
@SpringBootApplication
public class ZenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZenApplication.class,args);
    }

}
