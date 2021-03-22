package com.cullenye.concurrent.ch6pool.schedule;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示ScheduledThreadPoolExecutor的用法
 */
public class ScheduledCase {

    public static void main(String[] args) {

        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);

        //延时Runnable任务（仅执行一次）
//        pool.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("the task only run once!");
//            }
//        },3000, TimeUnit.MILLISECONDS);

        // 固定延时间隔执行的任务,提交任务后延迟1秒执行，每隔3秒执行一次
//        pool.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("*******fixDelay start," + ScheduleWorker.formater.format(new Date()));
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("*******fixDelay end," + ScheduleWorker.formater.format(new Date()));
//            }
//        },1000,3000,TimeUnit.MILLISECONDS);

        //固定时间间隔执行的任务,从理论上说第二次任务在6000 ms后执行，第三次在6000*2 ms后执行
//        pool.scheduleAtFixedRate(
//                new ScheduleWorkerTime(),0,6000,
//                TimeUnit.MILLISECONDS);

        // 固定时间间隔执行的任务,开始执行后就触发异常,next周期将不会运行
//        pool.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HasException),
//                0, 3000, TimeUnit.MILLISECONDS);

        // 固定时间间隔执行的任务,虽然抛出了异常,但被捕捉了,next周期继续运行
        pool.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.ProcessException),
                0, 3000, TimeUnit.MILLISECONDS);

    }
}
