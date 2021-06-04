package com.cullenye.concurrent.ch8a;

import com.cullenye.concurrent.ch8a.vo.ITaskProcesser;
import com.cullenye.concurrent.ch8a.vo.JobInfo;
import com.cullenye.concurrent.ch8a.vo.TaskResult;
import com.cullenye.concurrent.ch8a.vo.TaskResultType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 框架的主体类，也是调用者主要使用的类
 * @author yeguanhong
 */
public class PendingJobPool {

    /**
     * 框架运行时的线程数，与机器的CPU数相同
     */
    private static final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();
    /**
     * 队列，线程池使用，用以存放待处理的任务
     */
    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(5000);
    /**
     * 线程池，固定大小，有界队列
     */
    private static ExecutorService taskExecutor = new ThreadPoolExecutor(THREAD_COUNTS,THREAD_COUNTS,60,TimeUnit.SECONDS,taskQueue);
    /**
     * 工作信息的存放容器
     */
    private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap = new ConcurrentHashMap<>();

    public static Map<String, JobInfo<?>> getJobMap(){
        return jobInfoMap;
    }

    /**
     * 以单例启动，防止调用者通过构造函数创建实例
     */
    private PendingJobPool(){}

    private static class JobPoolHolder{
        public static PendingJobPool pendingJobPool = new PendingJobPool();
    }

    public static PendingJobPool getInstance(){
        return JobPoolHolder.pendingJobPool;
    }

    /**
     * 调用者注册工作，提供工作名，任务的处理器等等
     */
    public <R> void registerJobInfo(String jobName,int jobLength,ITaskProcesser<?,?> taskProcesser,long expireTime){
        JobInfo<R> jobInfo = new JobInfo<>(jobName,jobLength,taskProcesser,expireTime);
        if(null !=jobInfoMap.putIfAbsent(jobName,jobInfo)){
            throw new RuntimeException(jobName+"已经注册！");
        }
    }

    /**
     * 根据工作名称获取工作
     */
    public <R> JobInfo<R> getJobInfo(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if(null == jobInfo)
            throw new RuntimeException(jobName+"是非法任务！");
        return jobInfo;
    }

    /**
     * 调用者提交工作中的任务
     */
    public <T,R> void putTask(String jobName,T processData){
        JobInfo<R> jobInfo = getJobInfo(jobName);
        PendingTask<R,T> pendingTask = new PendingTask<>(jobInfo,processData);
        taskExecutor.execute(pendingTask);
    }

    /**
     * 对工作中的任务进行包装，提交给线程池使用，
     * 并将处理任务的结果，写入缓存以供查询
     */
    private static class PendingTask<R,T> implements Runnable{

        private JobInfo<R> jobInfo;
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData) {
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @Override
        public void run() {
            R r = null;
            ITaskProcesser<T,R> taskProcesser = (ITaskProcesser<T,R>)jobInfo.getTaskProcesser();
            TaskResult<R> taskResult = null;

            // 防止使用框架的人员可能不处理异常，这里进行异常处理
            try {
                taskResult = taskProcesser.taskExecute(processData);
                if(null == taskResult){
                    taskResult = new TaskResult<R>(TaskResultType.Exception,r,"result is null");
                }
                if(null == taskResult.getTaskResultType()){
                    if(taskResult.getReason() == null){
                        taskResult = new TaskResult<R>(TaskResultType.Exception,r,"result is null");
                    }else{
                        taskResult = new TaskResult<R>(TaskResultType.Exception,r,"result is null,reason:" + taskResult.getReason());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                taskResult = new TaskResult<R>(TaskResultType.Exception,r,e.getMessage());
            } finally {
                jobInfo.addTaskResult(taskResult);
            }
        }
    }

    /**
     * 获得工作的整体处理进度
     */
    public <R> String getTaskProgress(String jobName){
        JobInfo<R> jobInfo = getJobInfo(jobName);
        return jobInfo.getTotalProcess();
    }

    /**
     * 获得每个任务的处理详情
     */
    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> jobInfo = getJobInfo(jobName);
        return jobInfo.getTaskDetail();
    }

}
