package xyz.wongs.drunkard.base.utils.thread;


import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/** 定义线程的基本单元
 * @author WCNGS@QQ.COM
 * @date 2017/12/2 14:51
 * @since 1.0.0
*/
public class ThreadUnit implements Runnable{

    private String value;

    public ThreadUnit(){

    }

    public ThreadUnit(String value){
        this.value=value;
    }

    @Override
    public void run() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间 "+localDateTime.getMinute()+":"+localDateTime.getSecond()+" 当前线程名: "+Thread.currentThread().getName()+" BEGIN "+value );
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
