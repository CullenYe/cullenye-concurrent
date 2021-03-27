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
}
