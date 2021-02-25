package com.cullenye.concurrent.ch6pool.mypool;

import java.util.Random;

public class TestMyThreadPool {

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool myThreadPool = new MyThreadPool(0,3);
        myThreadPool.execute(new MyTask("testA"));
        myThreadPool.execute(new MyTask("testB"));
        myThreadPool.execute(new MyTask("testC"));
        myThreadPool.execute(new MyTask("testD"));
        myThreadPool.execute(new MyTask("testE"));
        System.out.println(myThreadPool);
        Thread.sleep(10000);
        // 所有线程都执行完成才destory
        myThreadPool.destroy();
        System.out.println(myThreadPool);
    }

    /**
     * 任务类
     */
    static class MyTask implements Runnable{

        private String name;
        private Random r = new Random();

        public MyTask(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(r.nextInt(1000) + 2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getId() + " sleep InterruptedException:"
                    +Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
            System.out.println("任务 " + name + " 完成");
        }
    }
}
