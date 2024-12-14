package io.github.rothschil;

import io.github.rothschil.common.base.persistence.repository.BaseRepositoryFactoryBean;
import io.github.rothschil.common.config.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@ServletComponentScan(basePackages = {"io.github.rothschil"}) //扫描使用注解方式的Servlet
@EnableJpaRepositories(basePackages = {"io.github.rothschil.**.repository"},
		repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class//指定自己的工厂类
)
public class RothschilApplication {

	public static void main(String[] args) {
		SpringApplication app =new SpringApplication(RothschilApplication.class);
		Environment env = app.run(args).getEnvironment();
		Version.printlnVersionInfo(env);
	}

}
