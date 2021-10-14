package io.github.rothschil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import io.github.rothschil.base.persistence.jpa.repository.factory.BaseRepositoryFactoryBean;

/**
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:03
 * @since 1.0.0
*/
@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"io.github"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class
)
public class LocationJapApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationJapApplication.class,args);
    }

}

