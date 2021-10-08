package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import xyz.wongs.drunkard.base.persistence.jpa.repository.factory.BaseRepositoryFactoryBean;

/**
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:03
 * @since 1.0.0
*/
@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"xyz.wongs"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class
)
public class SkyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class,args);
    }

}

