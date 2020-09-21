package com.cullenye.concurrent.ch1base.base;

/**
 * 测试volatile
 * @author yeguanhong
 * @date 2020-09-15 19:15:29
 */
public class TestVolatile {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) {
        final TestVolatile test = new TestVolatile();
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"启动");
                    for(int j=0;j<1000;j++)
                        test.increase();
                    System.out.println(Thread.currentThread().getName()+"计算完成");
                }
            }.start();
        }

        //保证前面的线程都执行完
        while(Thread.activeCount()>2){
            System.out.println(Thread.currentThread().getName()+"继续等待："+Thread.activeCount());
        }
        Thread.yield();
        System.out.println(test.inc);
    }
}