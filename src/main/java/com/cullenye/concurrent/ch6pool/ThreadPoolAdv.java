package com.cullenye.concurrent.ch6pool;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池中线程的创建方式，把线程设置为守护线程
 */
public class ThreadPoolAdv {

    static class Worker implements Runnable
    {
        private String taskName;
        private Random r = new Random();

        public Worker(String taskName){
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }

        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName()
                    +" process the task : " + taskName);
            try {
                Thread.sleep(r.nextInt(100)*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyThreadFactory implements ThreadFactory{

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r,"Cullen" + count.getAndIncrement());
            thread.setDaemon(true);
            System.out.println("create "+thread);
            return thread;
        }
    }

    public static void main(String[] args) {

        ExecutorService pool = new ThreadPoolExecutor(2,
                4,3,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),
                new MyThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for(int i=0;i<=6;i++){
            Worker worker = new Worker("worker " + i);
            System.out.println("A new task has been added : " + worker.getName());
            pool.execute(worker);
        }
    }
}
