package com.cullenye.concurrent.ch6pool.completionservice;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CompletionService使用示例，并和普通对接队列存储线程结果对比
 */
public class CompletionCase {

    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors() * 10;

    // 方法一，自己写集合来实现获取线程池中任务的返回结果
    public void testByQueue() throws Exception {
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);

        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        // 队列,拿任务的执行结果
        BlockingQueue<Future<Integer>> queue = new LinkedBlockingDeque<>();

        // 向里面扔任务
        for(int i=0;i<TOTAL_TASK;i++){
            Future<Integer> future = pool.submit(new WorkTask("ExecTask" + i));
            queue.add(future);
        }

        // 检查线程池任务执行结果
        for(int i=0;i< TOTAL_TASK;i++){
            int sleptTime = queue.take().get();
            System.out.println(" slept "+sleptTime+" ms ...");
            count.addAndGet(sleptTime);
        }

        // 关闭线程池
        pool.shutdown();
        System.out.println("任务休眠总时间"+count.get() + "毫秒,方法一花费时间" + (System.currentTimeMillis()-start)+"毫秒");
    }

    // 方法二，通过CompletionService来实现获取线程池中任务的返回结果
    public void testByCompletion() throws Exception{
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);

        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(pool);

        // 向里面扔任务
        for(int i=0;i<TOTAL_TASK;i++){
            completionService.submit(new WorkTask("ExecTask" + i));
        }

        // 检查线程池任务执行结果
        for(int i=0;i<TOTAL_TASK;i++){
            int sleptTime = completionService.take().get();
            System.out.println(" slept "+sleptTime+" ms ...");
            count.addAndGet(sleptTime);
        }

        // 关闭线程池
        pool.shutdown();
        System.out.println("任务休眠总时间"+count.get() + "毫秒,方法二花费时间" +(System.currentTimeMillis()-start)+"毫秒");
    }

    public static void main(String[] args) throws Exception{
        CompletionCase completionCase = new CompletionCase();
        completionCase.testByQueue();
        completionCase.testByCompletion();
    }
}
