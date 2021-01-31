package com.cullenye.concurrent.ch5container.usedelayqueue;

import java.util.concurrent.DelayQueue;

/**
 * 测试类
 * @author yeguanhong
 * @date 2021-01-31 19:48:44
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<ItemVo<Order>> delayQueue = new DelayQueue<>();

        new Thread(new PutThread(delayQueue)).start();
        new Thread(new GetThread(delayQueue)).start();

        for(int i=1;i<500;i++){
            Thread.sleep(500);
            System.out.println(i * 500);
        }
    }
}
