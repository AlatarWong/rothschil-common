package io.github.rothschil.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2022/4/21 - 10:36
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/offline/service-site")
public class OfflineServiceWeb {

    @Autowired
    private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;

    @RequestMapping("/online")
    public String online() {
        this.eurekaAutoServiceRegistration.start();
        return "execute online method, online success.";
    }

    @RequestMapping("/offline")
    public String offline() {
        this.eurekaAutoServiceRegistration.stop();
        return "execute offline method, offline success.";
    }
}
