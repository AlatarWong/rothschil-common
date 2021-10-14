package io.github.rothschil.base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.lionsoul.ip2region","io.github.rothschil"})
public abstract class BaseTest {
}
