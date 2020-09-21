package com.cullenye.concurrent.ch3cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 使用AtomicStampedReference
 * @author yeguanhong
 * @date 2020-09-22 00:01:02
 */
public class UseAtomicStampedReference {

    private static final AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("Karen",0);

    public static void main(String[] args) throws InterruptedException{
        final String oldReference = atomicStampedReference.getReference();
        final int oldStamp = atomicStampedReference.getStamp();
        System.out.println("原始变量值和版本戳:"+oldReference+"  "+oldStamp);

        Thread rightStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"当前变量值："+oldReference+"，当前版本戳："+oldStamp
                        +"，操作结果："+atomicStampedReference.compareAndSet(oldReference, "Cullen", oldStamp, oldStamp+1));
            }
        });

        Thread errorStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String reference = atomicStampedReference.getReference();
                System.out.println(Thread.currentThread().getName()+"当前变量值："+reference+"，当前版本戳："+atomicStampedReference.getStamp()
                        +"，操作结果："+atomicStampedReference.compareAndSet(reference, "Jack",oldStamp, oldStamp+1));
            }
        });

        Thread errorReferenceThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedReference.getStamp();
                System.out.println(Thread.currentThread().getName()+"当前变量值："+atomicStampedReference.getReference()+"，当前版本戳："+stamp
                        +"，操作结果："+atomicStampedReference.compareAndSet(oldReference, "Rose",stamp, stamp+1));
            }
        });

        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();
        errorReferenceThread.start();
        errorReferenceThread.join();
    }
}
