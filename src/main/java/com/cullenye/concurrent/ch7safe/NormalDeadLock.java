package com.cullenye.concurrent.ch7safe;

/**
 * 演示普通账户的死锁和解决
 */
public class NormalDeadLock {
    /**
     * 第一个锁
     */
    private static Object valueFirst = new Object();
    /**
     * 第二个锁
     */
    private static Object valueSecond = new Object();

    // 先拿第一个锁，再拿第二个锁
    private static void fisrtToSecond() throws InterruptedException{
        String threadName = Thread.currentThread().getName();
        synchronized (valueFirst){
            System.out.println(threadName+" get first");
            Thread.sleep(100);
            synchronized (valueSecond){
                System.out.println(threadName+" get second");
            }
        }
    }

    // 先拿第二个锁，再拿第一个锁
    private static void SecondToFisrt() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (valueSecond){
            System.out.println(threadName+" get second");
            Thread.sleep(100);
            synchronized (valueFirst){
                System.out.println(threadName+" get first");
            }
        }
    }

    private static class TestThread extends Thread{

        private String name;

        public TestThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try {
                SecondToFisrt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("主线程");
        TestThread testThread = new TestThread("子线程");
        testThread.start();
        try {
            fisrtToSecond();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
