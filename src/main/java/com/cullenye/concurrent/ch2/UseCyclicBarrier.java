package com.cullenye.concurrent.ch2;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * 使用CyclicBarrier
 * @author yeguanhong
 * @date 2020-09-15 20:10:49
 */
public class UseCyclicBarrier {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5,new CollectThread());

    /**
     * 存放子线程工作结果的容器
     */
    private static ConcurrentHashMap<String,Long> resultMap = new ConcurrentHashMap<>();

    /**
     * 工作子线程
     */
    private static class SubThread implements Runnable{

        @Override
        public void run() {
            //线程本身的工作结果
            long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId()+"",id);

            //随机决定工作线程是否睡眠
            Random r = new Random();
            try {
                if(r.nextBoolean()) {
                    Thread.sleep(2000+id);
                }
                System.out.println("线程"+id+"到达屏障，正在等待");
                cyclicBarrier.await();
                Thread.sleep(1000+id);
                System.out.println("线程"+id+"在屏障开发后完成业务操作");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 屏障开放后执行的线程，将容器中所有工作子线程的工作结果进行字符串拼接，并输出
     */
    private static class CollectThread implements Runnable{

        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for(Map.Entry<String,Long> subThreadResult:resultMap.entrySet()){
                result.append("["+subThreadResult.getValue()+"]");
            }
            System.out.println("CollectThread线程取得的所有子线程工作结果： " + result);
            System.out.println("CollectThread线程完成其它工作");
        }
    }

    public static void main(String[] args) {
        //启动5个工作子线程
        for(int i=0;i<5;i++){
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }
}
