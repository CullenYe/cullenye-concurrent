package com.cullenye.concurrent.ch2.forkjoin.sum;

import java.util.Random;

/**
 * 创建用于求和计算的数组
 * @author yeguanhong
 * @date 2020-06-16 23:19:52
 */
public class MakeArray {

    public static final int LENGTH = 100000000;

    public static int[] getArray(){
        int[] array = new int[LENGTH];
        Random random = new Random();
        for(int i=0;i<array.length;i++){
            array[i] = random.nextInt(LENGTH*3);
        }
        return array;
    }
}
