package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import xyz.wongs.drunkard.base.property.listener.PropertiesListener;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/24 - 10:16
 * @version 1.0.0
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AlipayApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AlipayApplication.class);
        // 第四种方式：注册监听器
        application.addListeners(new PropertiesListener("oss-pay2.properties"));
        application.run(args);
    }
}
