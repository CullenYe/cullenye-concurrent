package com.cullenye.concurrent.ch6pool.mypool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 自己实现的线程池
 * @author yeguanhong
 * @date 2021-02-09 15:32:02
 */
public class MyThreadPool {

    /**
     * 缺省线程数据量
     */
    private static final int WORK_COUNT = 5;
    /**
     * 存放任务的容器
     */
    private final BlockingQueue<Runnable> taskQueue;
    /**
     * 工作线程
     */
    private final int work_number;
    private WorkThread[] workThreads;

    /**
     * 写基础框架，尽量提供缺省值
     */
    public MyThreadPool(){
        this(100,WORK_COUNT);
    }

    public MyThreadPool(int task_count,int work_number){
        if(work_number <= 0) work_number = WORK_COUNT;
        if(task_count <= 0) task_count = 100;
        this.taskQueue = new ArrayBlockingQueue<>(task_count);
        this.work_number = work_number;
        workThreads = new WorkThread[work_number];
        // 工作线程初始化
        for(int i=0;i<work_number;i++){
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }

    /**
     * 放入任务，但只是加入队列
     */
    public void execute(Runnable task){
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁线程池
     */
    public void destroy(){
        System.out.println("准备关闭线程池");
        for(int i=0;i<workThreads.length;i++){
            workThreads[i].stopWorker();
            // 帮助gc
            workThreads[i] = null;
        }
        // 清空任务队列
        taskQueue.clear();
    }

    @Override
    public String toString() {
        return "WorkThread number:"+work_number
                +" wait task number:"+taskQueue.size();
    }

    /**
     * 内部类，工作线程的实现
     */
    private class WorkThread extends Thread{
        @Override
        public void run() {
            while (!isInterrupted()){
                Runnable r = null;
                try {
                    r = taskQueue.take();
                    if(r != null){
                        System.out.println(getId() + "准备执行" + ((TestMyThreadPool.MyTask)r).getName());
                        r.run();
                    }
                    // 执行完后手动置为空
                    r = null;
                } catch (InterruptedException e) {
                    // 因为没有任务时taskQueue.take()会阻塞，此时如果销毁线程池，关闭工作线程，会抛异常，所以不打印异常
                    //e.printStackTrace();
                }
            }
        }

        /**
         * 停止工作线程
         */
        public void stopWorker() {
            interrupt();
        }
    }

}
