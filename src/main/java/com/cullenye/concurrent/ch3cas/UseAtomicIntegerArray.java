package com.cullenye.concurrent.ch3cas;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 演示使用
 * @author yeguanhong
 * @date 2020-09-21 23:28:44
 */
public class UseAtomicIntegerArray {

    static int[] array = new int[]{1,2};

    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(array);

    public static void main(String[] args) {

        // 取得第一个元素的值，并更新为3
        System.out.println(atomicIntegerArray.getAndSet(0,3));
        System.out.println(atomicIntegerArray.get(0));
        // 原数组不会变化
        System.out.println(array[0]);
    }
}
