package io.github.rothschil;

import io.github.rothschil.base.persistence.jpa.repository.factory.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/12/4 - 18:25
 * @since 1.0.0
 */
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"io.github"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class
)
@SpringBootApplication(scanBasePackages = {"io.github.rothschil"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }


}
