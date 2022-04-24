package io.github.rothschil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/6 - 21:27
 * @since 1.0.0
 */
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
public class WillApplication {

    public static void main(String[] args) {
        SpringApplication.run(WillApplication.class, args);
    }
}
