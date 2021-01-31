package com.cullenye.concurrent.ch4lockaqs.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 实现自己的独占锁
 * @author yeguanhong
 * @date 2020-10-01 18:16:05
 */
public class SelfLock implements Lock {

    /**
     * 同步器的定义
     */
    private static class Sync extends AbstractQueuedSynchronizer{
        /**
         * 获得所
         */
        @Override
        protected boolean tryAcquire(int arg) {
            // 通过CAS原子操作修改状态，如果操作成功，说明当前线程抢到了锁
            if(compareAndSetState(0,1)){
                // 设置当前拥有独占访问的线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 释放锁
         */
        @Override
        protected boolean tryRelease(int arg) {
            if(getState() == 0){
                // 当前锁的状态不对
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            // 因为当前线程已经获得锁，所以不需要使用CAS修改涨停
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition(){
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        System.out.println(Thread.currentThread().getName()+" ready get lock");
        sync.acquire(1);
        System.out.println(Thread.currentThread().getName()+" already got lock");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName()+" ready release lock");
        sync.release(1);
        System.out.println(Thread.currentThread().getName()+" already released lock");
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    /**
     * 判断锁是否被占用
     */
    public boolean isLock(){
        return sync.isHeldExclusively();
    }

    /**
     * 判断有没有线程在等待许可
     */
    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }
}
