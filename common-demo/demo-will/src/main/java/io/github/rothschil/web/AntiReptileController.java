package io.github.rothschil.web;

import cn.keking.anti_reptile.annotation.AntiReptile;
import io.github.rothschil.service.AsynService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequestMapping("/demo")
@RestController
public class AntiReptileController {

    @Autowired
    private AsynService asynService;

    @AntiReptile
    @GetMapping("")
    public String demo(){
        return "demo";
    }

    @GetMapping("/asyn")
    public String asyn(){
        String selector = asynService.selector();
        asynService.update(selector);
        log.warn("阅读执行完毕");
        return selector;
    }

    @GetMapping("/asyn2")
    public String asyn2(){
        String selector = asynService.selector();
        CompletableFuture<String> future = asynService.modification(selector);
        String result = "";
        try {
            result = todoFuture(future);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        log.warn("阅读执行完毕");
        return selector + result;
    }

    private String todoFuture(CompletableFuture<String> future) throws ExecutionException, InterruptedException {
        String result = "";
        while(true){
            if(future.isCancelled()){
                log.error("任务队列取消");
                break;
            }
            if(future.isDone()){
                result = future.get();
            }
        }
        return result;
    }
}
