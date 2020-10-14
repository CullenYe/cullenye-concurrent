package com.cullenye.concurrent.cha4lockaqs.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 共享锁实现的同步工具类，允许多个线程同时访问，超过则被阻塞
 * @author yeguanhong
 * @date 2020-10-14 22:50:09
 */
public class TrinityLock implements Lock{

    private static final class Sync extends AbstractQueuedSynchronizer{

        // 通过构造函数确定允许同时访问的线程数
        Sync(int count){
            if(count <= 0){
                throw new IllegalArgumentException("count must large than zero.");
            }
            setState(count);
        }

        /**
         * @param reduceCount 扣减个数
         * @return 返回小于0，表示当前线程获得同步状态失败,大于0，表示当前线程获得同步状态成功
         */
        @Override
        protected int tryAcquireShared(int reduceCount) {
            for(;;){
                int current = getState();
                int newCount = current - reduceCount;
                if(newCount < 0 || compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }

        /**
         * @param releaseCount 释放个数
         */
        @Override
        protected boolean tryReleaseShared(int releaseCount) {
            for(;;){
                int current = getState();
                int newCount = current + releaseCount;
                if(compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }

        final ConditionObject newCondition(){
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync(3);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
