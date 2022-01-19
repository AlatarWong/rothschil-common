package io.github.rothschil.domain.web;

import io.github.rothschil.base.aop.queue.AbstractQueue;
import io.github.rothschil.task.FileTaskComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/tidb")
public class TiDbController {

    private static final Logger log = LoggerFactory.getLogger(TiDbController.class);


    @GetMapping("/blunk")
    public void blunk() {
        String path = "H:\\Download\\temp\\ct_portal\\ct_portal.2022-01-08.log";
        fileTaskComponent.executeJsonByFile(path);
    }


    @GetMapping("/get")
    public int getQueueSize() {
        return abstractQueue.getQueueSize();
    }

    @GetMapping("/status")
    public boolean running() {
        return abstractQueue.isRunning();
    }

    @GetMapping("/fut")
    public Future<?> threadStatus() {
        return abstractQueue.threadStatus();
    }

    private FileTaskComponent fileTaskComponent;

    private AbstractQueue abstractQueue;

    @Autowired
    public void setFileTaskComponent(FileTaskComponent fileTaskComponent) {
        this.fileTaskComponent = fileTaskComponent;
    }

    @Autowired
    public void setAbstractQueue(AbstractQueue abstractQueue) {
        this.abstractQueue = abstractQueue;
    }

}

