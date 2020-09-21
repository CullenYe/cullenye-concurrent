package com.cullenye.concurrent.ch1base.base;

/**
 * 测试synchronized
 * @author yeguanhong
 * @date 2020-09-15 19:15:01
 */
public class TestSynchronized extends Thread{

    private static int count = 0;

    @Override
    public void run() {
        synchronized(TestSynchronized.class)
        {
            for(int i=1;i<=100000;i++)
            {
                TestSynchronized.count++;
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        TestSynchronized[] testSynchronizeds = new TestSynchronized[10];
        for(TestSynchronized testSynchronized : testSynchronizeds)
        {
            testSynchronized = new TestSynchronized();
            testSynchronized.start();
        }
        Thread.sleep(2000);
        System.out.println(TestSynchronized.count);
    }
}
