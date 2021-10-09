package xyz.wongs.drunkard.thread;


import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/** 线程池的自定义策略
 * @author WCNGS@QQ.COM
 * @date 20/11/19 16:20
 * @since 1.0.0
*/
public class PippinRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//        if (r instanceof ThreadTaskDemo) {
//            ThreadTaskDemo thTk = (ThreadTaskDemo) r;
//            //为了演示用，所以直接打印，勿模仿。正式场景下应该持久化或者写入日志！！！
//            System.out.println("当前任务 "+ thTk.getValue()+" 执行失败！");
//        }
    }
}
