//package xyz.wongs.drunkard.image.task;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import xyz.wongs.drunkard.base.BaseTest;
//
//@DisplayName("忽略测试")
//public class BiilDemo extends BaseTest {
//
//    private static Logger LOG = LoggerFactory.getLogger(ImageInfoCase.class);
//
//    @Autowired
//    public RunFileTask runFileTask;
//
//    @Disabled
//    @Test
//    public void testAdd(){
//        long start  = System.currentTimeMillis();
//        runFileTask.run("E:\\Repertory\\Lightroom\\Exp");
//        long end  = System.currentTimeMillis();
//        LOG.info("耗时 cost ={} 秒",(end-start)/1000);
//    }
//}
