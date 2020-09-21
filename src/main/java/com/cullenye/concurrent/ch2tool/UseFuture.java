package com.cullenye.concurrent.ch2tool;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用Future
 * @author yeguanhong
 * @date 2020-09-15 20:44:35
 */
public class UseFuture {

    private static class UseCallable implements Callable<Integer>{

        private int sum;
        @Override
        public Integer call() throws Exception {
            System.out.println("Callable子线程开始计算");
            Thread.sleep(2000);
            for(int i=0;i<5000;i++) {
                sum = sum+i;
            }
            System.out.println("Callable子线程计算完成，结果="+sum);
            return sum;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        UseCallable useCallable = new UseCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(useCallable);
        new Thread(futureTask).start();

        Thread.sleep(1000);

        Random random = new Random();
        if(random.nextBoolean()){
            System.out.println("获取计算结果 ="+futureTask.get());
        }else{
            System.out.println("中断计算");
            futureTask.cancel(true);
        }
    }
}
