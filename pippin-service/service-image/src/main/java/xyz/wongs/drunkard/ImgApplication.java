package xyz.wongs.drunkard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * EnableJpaAuditing SpringBootApplication
 * EnableJpaRepositories(basePackages = {"xyz.wongs.drunkard"},
 * repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class//Specify your own factory class
 * )
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:03
 * @since 1.0.0
 */
@MapperScan(basePackages = {"xyz.wongs.**.mapper"})
@SpringBootApplication
public class ImgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImgApplication.class, args);
    }

}

