package io.github.rothschil.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AsynService {

    public String selector(){
        return "Selector "+ Thread.currentThread().getName();
    }

    @Async("threadPoolTaskExecutor")
    public String update(String selector){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.warn(selector);
        return "Update "+ Thread.currentThread().getName();
    }

    @Async
    public CompletableFuture<String> modification(String selector){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.warn(selector);
        return CompletableFuture.completedFuture("Update "+ Thread.currentThread().getName());

    }
}
