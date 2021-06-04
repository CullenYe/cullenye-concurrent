package com.cullenye.concurrent.ch8a.demo;

import com.cullenye.concurrent.ch8a.PendingJobPool;
import com.cullenye.concurrent.ch8a.vo.TaskResult;

import java.util.List;
import java.util.Random;

/**
 * 模拟一个应用程序，提交工作和任务，并查询任务进度
 * @author yeguanhong
 */
public class AppTest {

    private static final String JOB_NAME = "计算数值";
    private static final int JOB_LENGTH = 1000;

    private static class QueryResult implements Runnable{

        private final PendingJobPool pendingJobPool;

        public QueryResult(PendingJobPool pendingJobPool) {
            this.pendingJobPool = pendingJobPool;
        }

        @Override
        public void run() {
            int i=0;
            while (i<350){
                List<TaskResult<String>> taskResults = pendingJobPool.getTaskDetail(JOB_NAME);
                if(!taskResults.isEmpty()){
                    System.out.println(pendingJobPool.getTaskProgress(JOB_NAME));
                    System.out.println(taskResults);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {
        MyTaskProcesser myTaskProcesser = new MyTaskProcesser();
        PendingJobPool pendingJobPool = PendingJobPool.getInstance();
        pendingJobPool.registerJobInfo(JOB_NAME,JOB_LENGTH,myTaskProcesser,5);
        Random random = new Random();
        for(int i=0;i<JOB_LENGTH;i++){
            pendingJobPool.putTask(JOB_NAME,random.nextInt(1000));
        }
        Thread thread = new Thread(new QueryResult(pendingJobPool));
        thread.start();
    }
}
