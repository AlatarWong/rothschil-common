package xyz.wongs.drunkard.base.utils.thread;


import java.util.concurrent.*;

/** 手工创建线程池
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/26 - 17:29
 * @version 1.0.0
 */
public class ThreadPoolsUtil {


    /**
     * @param corePoolSize  核心线程数
     * @param maximumPoolSize   允许并行最大核心线程数
     * @param theadName 指定线程名字
     * @return java.util.concurrent.ExecutorService
     * @throws
     * @date 20/11/19 16:23
     */
    public static ThreadPoolExecutor doCreate(int corePoolSize,int maximumPoolSize, String theadName){
        return doCreate(corePoolSize, maximumPoolSize,0,theadName);
    }

    /**
     * @param corePoolSize  核心线程数
     * @param maximumPoolSize   允许并行最大核心线程数
     * @param queueSize 有界队列的大小
     * @param theadName 指定线程名字
     * @return java.util.concurrent.ExecutorService
     * @throws
     * @date 20/11/19 16:23
     */
    public static ThreadPoolExecutor doCreate(int corePoolSize,int maximumPoolSize,
                                                      int queueSize,
                                                      String theadName){
        return doCreate(corePoolSize, maximumPoolSize,0, TimeUnit.SECONDS, queueSize,theadName);
    }

    /**
     * @param corePoolSize  核心线程数
     * @param maximumPoolSize   允许并行最大核心线程数
     * @param keepAliveTime 当线程数大于内核数时，这是多余的空闲线程将在终止之前等待新任务的最长时间
     * @param unit  时间的单位 秒 毫秒等
     * @param queueSize 有界队列的大小
     * @param theadName 指定线程名字
     * @return java.util.concurrent.ExecutorService
     * @throws
     * @date 20/11/19 16:23
     */
    public static ThreadPoolExecutor doCreate(int corePoolSize,int maximumPoolSize,int keepAliveTime,TimeUnit unit,
                                                      int queueSize,
                                                      String theadName){
        // 1、指定有界队列，并明确大小
        queueSize= queueSize==0?8:queueSize;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(queueSize);

        // 2、自定义线程名字
        ThreadFactory threadFactory = new PippinThreadFactory(theadName);



        return doCreate(corePoolSize, maximumPoolSize,keepAliveTime, unit, queue,threadFactory, getRejectedExecutionHandler());
    }

    /**
     * @param corePoolSize  核心线程数
     * @param maximumPoolSize   允许并行最大核心线程数
     * @param keepAliveTime 当线程数大于内核数时，这是多余的空闲线程将在终止之前等待新任务的最长时间
     * @param unit    时间的单位
     * @param workQueue 在执行任务之前用于保留任务的队列，此队列将仅保存execute方法提交的Runnable任务。
     * @param theadName   指定线程名字
     * @return java.util.concurrent.ExecutorService
     * @throws
     * @date 20/11/19 16:23
     */
    public static ThreadPoolExecutor doCreate(int corePoolSize,int maximumPoolSize,int keepAliveTime,TimeUnit unit,
                                               BlockingQueue<Runnable> workQueue,
                                               String theadName){
        // 初始化大小
        int poolSize = getCorePoolSize(corePoolSize);
        // 2、自定义线程名字
        ThreadFactory threadFactory = new PippinThreadFactory(theadName);
        // 3、自定义线程池超出容量的拒绝策略
        RejectedExecutionHandler policy = new PippinRejectedExecutionHandler();
        return new ThreadPoolExecutor(poolSize, maximumPoolSize,keepAliveTime, unit, workQueue,threadFactory, policy);
    }


    public static RejectedExecutionHandler getRejectedExecutionHandler(){
        // 3、自定义线程池超出容量的拒绝策略
        return new PippinRejectedExecutionHandler();
    }

    /** 判断 核心线程池大小 是否 超出 CPU数量，设定合理的线程池大小
     * @param corePoolSize 核心数
     * @return int
     * @date 20/11/19 16:13
     */
    public static int getCorePoolSize(int corePoolSize){
        int cess = Runtime.getRuntime().availableProcessors();
        //核心线程池大小 超出 CPU数量两倍
        int bic = 2;
        if(corePoolSize > cess * bic){
            corePoolSize = cess * bic;
        }
        return corePoolSize;
    }

    /**
     * @param corePoolSize  核心线程数
     * @param maximumPoolSize   允许并行最大核心线程数
     * @param keepAliveTime 当线程数大于内核数时，这是多余的空闲线程将在终止之前等待新任务的最长时间
     * @param unit  时间的单位 秒 毫秒等
     * @param workQueue 在执行任务之前用于保留任务的队列，此队列将仅保存execute方法提交的Runnable任务。
     * @param threadFactory 执行程序创建新线程时要使用的工厂
     * @param handler   当线等待队列中的数量超过既定容量，所需要处理策略
     * @return java.util.concurrent.ExecutorService
     * @date 20/11/19 16:23
     */
    public static ThreadPoolExecutor doCreate(int corePoolSize,int maximumPoolSize,int keepAliveTime,TimeUnit unit,
             BlockingQueue<Runnable> workQueue,
             ThreadFactory threadFactory,
             RejectedExecutionHandler handler){
        // 初始化大小
        int poolSize = getCorePoolSize(corePoolSize);
        return new ThreadPoolExecutor(poolSize, maximumPoolSize,keepAliveTime, unit, workQueue,threadFactory, handler);
    }


    public static ScheduledExecutorService doCreate( int corePoolSize, String theadName){
        ThreadFactory threadFactory = new PippinThreadFactory(theadName);
        return doCreate(corePoolSize,threadFactory);
    }

    public static ScheduledExecutorService doCreate( int corePoolSize, ThreadFactory threadFactory){
        return new ScheduledThreadPoolExecutor (corePoolSize,threadFactory,getRejectedExecutionHandler());
    }

}
