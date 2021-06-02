package com.cullenye.concurrent.ch8a;

import com.cullenye.concurrent.ch8a.vo.JobInfo;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 框架的主体类，也是调用者主要使用的类
 * @author yeguanhong
 */
public class PendingJobPool {

    /**
     * 框架运行时的线程数，与机器的CPU数相同
     */
    private static final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();
    /**
     * 队列，线程池使用，用以存放待处理的任务
     */
    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(5000);
    /**
     * 线程池，固定大小，有界队列
     */
    private static ExecutorService taskExecutor = new ThreadPoolExecutor(THREAD_COUNTS,THREAD_COUNTS,60,TimeUnit.SECONDS,taskQueue);
    /**
     * 工作信息的存放容器
     */
    private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap = new ConcurrentHashMap<>();

    public static Map<String, JobInfo<?>> getJobMap(){
        return jobInfoMap;
    }

    /**
     * 以单例启动，防止调用者通过构造函数创建实例
     */
    private PendingJobPool(){}

    private static class JobPoolHolder{
        public static PendingJobPool pendingJobPool = new PendingJobPool();
    }

    public static PendingJobPool getInstance(){
        return JobPoolHolder.pendingJobPool;
    }


}
