package com.cullenye.concurrent.ch1base.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 新建线程
 * @author yeguanhong
 * @date 2020-09-15 19:14:11
 */
public class NewThread {

    private static class  MyThread extends Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    private static class MyRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    private static class MyCallable implements Callable<String>{
        @Override
        public String call() {
            System.out.println(Thread.currentThread().getName());
            return "CallResult";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();

        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();

        MyCallable myCallable = new MyCallable();
        FutureTask futureTask = new FutureTask(myCallable);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
