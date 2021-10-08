package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import xyz.wongs.drunkard.base.property.listener.PropListener;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/24 - 10:16
 * @since 1.0.0
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AlipayApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AlipayApplication.class);
        // 注册监听器
        application.addListeners(new PropListener("oss-pay2.properties"));
        application.run(args);
    }
}
