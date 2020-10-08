package com.cullenye.concurrent.cha4lockaqs.aqs;

import java.util.concurrent.locks.Lock;

/**
 * 测试自己的可重入锁
 * @author yeguanhong
 * @date 2020-10-01 20:59:16
 */
public class TestReenterSelfLock {

    static final Lock lock = new ReenterSelfLock();

    public void reenter(int depth){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+" 深度："+depth);
            int temp = depth - 1;
            if(temp == 0) {
                return;
            }else{
                reenter(temp);
            }
        }finally {
            lock.unlock();
        }
    }

    public void test(){

        class Worker extends Thread{

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reenter(3);
            }
        }

        for(int i=0;i<3;i++){
            Worker worker = new Worker();
            worker.start();
        }

        for(int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TestReenterSelfLock testReenterSelfLock = new TestReenterSelfLock();
        testReenterSelfLock.test();
    }
}
