package com.cullenye.concurrent.ch2tool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * 使用Exchanger
 * @author yeguanhong
 * @date 2020-09-15 20:25:39
 */
public class UseExchanger {

    private static Exchanger<Set<String>> exchanger = new Exchanger<>();

    public static void main(String[] args) {

        // 第一个线程
        new Thread(){
            @Override
            public void run() {
                Set<String> setA = new HashSet<>();
                try {
                    setA.add("DataA1");
                    setA.add("DataA2");
                    setA = exchanger.exchange(setA);
                    // 输出交换后的数据
                    for(String data : setA)
                    {
                        System.out.println("setA："+data);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        // 第二个线程
        new Thread(){
            @Override
            public void run() {
                Set<String> setB = new HashSet<>();
                try {
                    setB.add("DataB1");
                    setB.add("DataB2");
                    setB.add("DataB3");
                    setB = exchanger.exchange(setB);
                    // 输出交换后的数据
                    for(String data : setB)
                    {
                        System.out.println("setB："+data);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
