package com.cullenye.concurrent.cha4lockaqs.aqs;

import java.util.concurrent.locks.Lock;

/**
 * 测试共享锁实现的同步工具类TrinityLock
 * @author yeguanhong
 * @date 2020-10-01 18:17:04
 */
public class TestTrinityLock {

    public void test(){

        final Lock lock = new TrinityLock();

        class WorkerThread extends Thread{
            @Override
            public void run() {
                lock.lock();
                try{
                    try {
                        System.out.println(Thread.currentThread().getName()+"取得了锁");
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
        for(int i=0;i<10;i++){
            WorkerThread worker = new WorkerThread();
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
        TestTrinityLock testSelfLock = new TestTrinityLock();
        testSelfLock.test();
    }
}
