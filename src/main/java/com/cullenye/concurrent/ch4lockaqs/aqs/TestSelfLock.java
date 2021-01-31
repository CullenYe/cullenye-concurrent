package com.cullenye.concurrent.ch4lockaqs.aqs;

import java.util.concurrent.locks.Lock;

/**
 * 测试自己的独占锁
 * @author yeguanhong
 * @date 2020-10-01 18:17:04
 */
public class TestSelfLock {

    public void test(){

        //final Lock lock = new ReentrantLock();
        final Lock lock = new SelfLock();

        /**
         * 工作线程，负责输出线程名称和睡眠
         */
        class Worker extends Thread{
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                lock.lock();
                try{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }
            }
        }

        // 启动4个工作线程
        for(int i=0;i<4;i++){
            Worker worker = new Worker();
            worker.start();
        }

        // 主线程每隔1秒换行
        for(int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("========================");
        }
    }

    public static void main(String[] args) {
        TestSelfLock testSelfLock = new TestSelfLock();
        testSelfLock.test();
    }
}
