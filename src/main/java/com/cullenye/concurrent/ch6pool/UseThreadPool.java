package com.cullenye.concurrent.ch6pool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程池的使用范例
 */
public class UseThreadPool {

    /**
     * 没有返回值
     */
    static class Worker implements Runnable{

        private String taskName;
        Random random = new Random();

        public Worker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    +" process the task : " + taskName);
            try {
                Thread.sleep(random.nextInt(100) * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 有返回值
     */
    static class CallWorker implements Callable<String>{

        private String taskName;
        Random random = new Random();

        public CallWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()
                    +" process the task : " + taskName);
            return Thread.currentThread().getName() + ":" + random.nextInt(100)*5;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 获得当前设备的CPU个数
        System.out.println(Runtime.getRuntime().availableProcessors());

//        ExecutorService threadPool = new ThreadPoolExecutor(2,3,
//                4, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(10),
//                new ThreadPoolExecutor.DiscardOldestPolicy());

        //ExecutorService threadPool = Executors.newFixedThreadPool(2);
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        //ExecutorService threadPool = Executors.newWorkStealingPool();

        for(int i=0;i<=6;i++){
            Worker worker = new Worker("worker" + i);
            System.out.println("A new task has been added : " + worker.getTaskName());
            threadPool.execute(worker);
        }

        for(int i=0;i<=6;i++){
            CallWorker callWorker = new CallWorker("callWorker" + i);
            System.out.println("A new task has been added : " + callWorker.getTaskName());
            Future<String> result = threadPool.submit(callWorker);
            System.out.println(result.get());
        }

        threadPool.shutdown();
    }
}
