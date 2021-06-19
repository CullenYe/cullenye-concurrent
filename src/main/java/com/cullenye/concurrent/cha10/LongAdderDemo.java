package com.cullenye.concurrent.cha10;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder,原子类以及同步锁性能测试
 * @author yeguanhong
 */
public class LongAdderDemo {
    private static final int MAX_THREAD = 200;
    private static final int TASK_COUNT = 400;
    private static final int TARGET_COUNT = 100000000;

    /**
     * 三个不同类型的long有关的变量
     */
    private final AtomicLong atomicLongCount = new AtomicLong(0L);
    private final LongAdder longAdderCount = new LongAdder();
    private long count = 0;

    /**
     * 控制线程同时进行
     */
    private static final CountDownLatch countDownLatchSynch = new CountDownLatch(TASK_COUNT);
    private static final CountDownLatch countDownLatchAtomicLong = new CountDownLatch(TASK_COUNT);
    private static final CountDownLatch countDownLatchLongAdder = new CountDownLatch(TASK_COUNT);

    /**
     * 普通long的同步锁测试方法
     */
    protected synchronized long increase(){
        return ++count;
    }

    protected synchronized long getCount(){
        return count;
    }

    /**
     * 普通long的同步锁测试任务
     */
    public class SyncTask implements Runnable{
        private final long startTime;
        /**
         * 因为需要使用LongAdderDemo的加锁方法操作count，所以需要传入该对象
         */
        private final LongAdderDemo longAdderDemo;

        public SyncTask(long startTime, LongAdderDemo longAdderDemo) {
            this.startTime = startTime;
            this.longAdderDemo = longAdderDemo;
        }

        @Override
        public void run() {
            long value = longAdderDemo.getCount();
            while (value < TARGET_COUNT){
                value = increase();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("SyncTask spend:" + (endTime - startTime) + "ms" );
            countDownLatchSynch.countDown();
        }
    }

    /**
     * 普通long的执行同步锁测试
     */
    public void testSync() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREAD);
        long startTime = System.currentTimeMillis();
        SyncTask syncTask = new SyncTask(startTime,this);
        for(int i=0;i<TASK_COUNT;i++){
            pool.submit(syncTask);
        }
        countDownLatchSynch.await();
        pool.shutdown();
    }

    /**
     * 原子型long的测试任务
     */
    public class AtomicLongTask implements Runnable{

        private final long startTime;

        public AtomicLongTask(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long value = atomicLongCount.get();
            while (value < TARGET_COUNT){
                value = atomicLongCount.incrementAndGet();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("AtomicLongTask spend:" + (endTime - startTime) + "ms");
            countDownLatchAtomicLong.countDown();
        }
    }

    /**
     * 原子型long的执行测试
     */
    public void testAtomicLong() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREAD);
        long startTime = System.currentTimeMillis();
        for(int i=0;i<TASK_COUNT;i++){
            AtomicLongTask atomicLongTask = new AtomicLongTask(startTime);
            pool.submit(atomicLongTask);
        }
        countDownLatchAtomicLong.await();
        pool.shutdown();
    }

    /**
     * LongAdder的测试任务
     */
    public class LongAdderTask implements Runnable {
        private final long startTime;

        public LongAdderTask(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long value = longAdderCount.sum();
            while (value < TARGET_COUNT) {
                longAdderCount.increment();
                value = longAdderCount.sum();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("LongAdderTask spend:" + (endTime - startTime) + "ms");
            countDownLatchLongAdder.countDown();
        }
    }

    /**
     * LongAdder的执行测试
     */
    public void testLongAdder() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREAD);
        long startTime = System.currentTimeMillis();
        LongAdderTask longAdderTask = new LongAdderTask(startTime);
        for (int i = 0; i < TASK_COUNT; i++) {
            pool.submit(longAdderTask);
        }
        countDownLatchLongAdder.await();
        pool.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        LongAdderDemo demo = new LongAdderDemo();
        //demo.testSync();
        //demo.testAtomicLong();
        demo.testLongAdder();
    }

}
