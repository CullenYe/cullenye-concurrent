package com.cullenye.concurrent.ch1base.base.safeend;

/**
 * 安全地停止Thread
 * @author yeguanhong
 * @date 2020-06-03 22:30:10
 */
public class EndThread {

    private static class MyThread extends Thread{
        @Override
        public void run() {
            while(!isInterrupted())
            {
                System.out.println(Thread.currentThread().getName() + "是否停止：" + isInterrupted());
            }
            System.out.println(Thread.currentThread().getName()+"线程结束了，是否停止：" + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        MyThread myThread = new MyThread();
        myThread.start();
        Thread.sleep(20);
        myThread.interrupt();

    }
}
