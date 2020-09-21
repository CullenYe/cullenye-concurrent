package com.cullenye.concurrent.ch3cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用AtomicInteger
 * @author yeguanhong
 * @date 2020-09-21 23:21:33
 */
public class UseAtomicInteger {

    private static AtomicInteger atomicInteger = new AtomicInteger(10);

    public static void main(String[] args) {

        //先获取值10，再加1
        System.out.println(atomicInteger.getAndIncrement());
        //先加1，再获取加1后的值
        System.out.println(atomicInteger.incrementAndGet());
        //先加11，再获取加11后的值
        System.out.println(atomicInteger.addAndGet(11));
        System.out.println(atomicInteger.get());
    }
}
