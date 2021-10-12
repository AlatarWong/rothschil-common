package xyz.wongs.drunkard.moon.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wongs.drunkard.task.RunFileTask;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 *
 * @date 2018/4/18 - 10:29
 * @since 1.0.0
 */
@RestController
public class ImageController {

    private static Logger LOG = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private RunFileTask runFileTask;

    @GetMapping("/ex")
    public void excute(){
        long start  = System.currentTimeMillis();
        runFileTask.run("E:\\Repertory\\Lightroom\\Exp");
        long end  = System.currentTimeMillis();
        LOG.info("耗时 cost ={} 秒",(end-start)/1000);
    }
}
