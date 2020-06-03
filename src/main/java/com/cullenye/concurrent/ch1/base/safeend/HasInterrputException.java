package com.cullenye.concurrent.ch1.base.safeend;

/**
 * 抛出InterruptedException是，在catch中再次调用interrupt()
 * @author yeguanhong
 * @date 2020-06-03 23:04:29
 */
public class HasInterrputException {

    private static class MyThread extends Thread{
        @Override
        public void run() {
            while(!isInterrupted())
            {
                System.out.println(Thread.currentThread().getName() + "是否停止：" + isInterrupted());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName()+"线程结束了，是否停止：" + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        MyThread myThread = new MyThread();
        myThread.start();
        Thread.sleep(100);
        myThread.interrupt();

    }
}
