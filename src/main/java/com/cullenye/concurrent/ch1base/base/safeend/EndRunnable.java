package com.cullenye.concurrent.ch1base.base.safeend;

/**
 * 安全地停止Runable
 * @author yeguanhong
 * @date 2020-06-03 22:30:10
 */
public class EndRunnable {

    private static class MyRunnable implements Runnable{
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted())
            {
                System.out.println(Thread.currentThread().getName() + "是否停止：" + Thread.currentThread().isInterrupted());
            }
            System.out.println(Thread.currentThread().getName()+"线程结束了，是否停止：" + Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new MyRunnable());
        thread.start();
        Thread.sleep(20);
        thread.interrupt();

    }
}
