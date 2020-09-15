package com.cullenye.concurrent.ch2;

import java.util.concurrent.CountDownLatch;

/**
 * 使用CountDownLatch
 * @author yeguanhong
 * @date 2020-09-15 20:10:21
 */
public class UseCountDownLatch {

    private static CountDownLatch countDownLatch = new CountDownLatch(6);

    //初始化线程
    private static class InitialThread implements Runnable{

        @Override
        public void run() {
            System.out.println("初始化线程Thread_"+Thread.currentThread().getId() +"准备开始初始化工作");
            //初始化工作内容......

            countDownLatch.countDown();

            //初始化完成后的其它工作......
            System.out.println("初始化线程Thread_"+Thread.currentThread().getId() +"已完成初始化后的其它工作");
        }

    }

    /**
     * 业务线程，需要等待初始化线程完成后才进行工作
     */
    private static class BusinessThread implements Runnable{
        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("业务线程"+Thread.currentThread().getId() +"继续完成工作");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // 开启单独的初始化线程，分2步初始化，countDownLatch需要扣减2次
        new Thread(() -> {
            System.out.println("单独的初始化线程开始初始化工作");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("单独的初始化线程完成了第1步初始化工作");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("单独的初始化线程完成了第2步初始化工作");
        }).start();

        //开启业务线程
        new Thread(new BusinessThread()).start();

        //开启4个初始化线程
        for(int i=0;i<=3;i++)
        {
            new Thread(new InitialThread()).start();
        }

        //主线程等待初始化工作完成
        countDownLatch.await();

        System.out.println("主线程等待初始化完成后开始进行其它工作");
    }
}
