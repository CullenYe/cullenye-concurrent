package com.cullenye.concurrent.ch8a.vo;

import com.cullenye.concurrent.ch8a.CheckJobProcessor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 提交给框架执行的工作实体类,
 * 工作：表示本批次需要处理的同性质任务(Task)的一个集合
 * @author yeguanhong
 */
public class JobInfo<R> {

    /**
     * 工作名，用以区分框架中唯一的工作
     */
    private final String jobName;
    /**
     * 工作中任务的长度
     */
    private final int jobLength;
    /**
     * 处理工作中任务的处理器
     */
    private final ITaskProcesser<?,?> taskProcesser;
    /**
     * 任务的成功次数
     */
    private AtomicInteger successCount;
    /**
     * 工作中目前已经处理的任务数量，包括成功和失败
     */
    private AtomicInteger taskProcessCount;
    /**
     * 存放每个任务处理结果的双向队列，供查询用，从尾部放入任务，从头部取任务
     */
    private LinkedBlockingDeque<TaskResult<R>> taskDetailQueues;
    /**
     * 保留的工作的结果信息供查询的时长
     */
    private final long expireTime;

    private static CheckJobProcessor checkJobProcessor = CheckJobProcessor.getInstance();

    public JobInfo(String jobName,int jobLength,ITaskProcesser<?,?> taskProcesser,long expireTime){
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcesser = taskProcesser;
        this.successCount = new AtomicInteger(0);
        this.taskProcessCount = new AtomicInteger(0);
        this.taskDetailQueues = new LinkedBlockingDeque<TaskResult<R>>(jobLength);
        this.expireTime = expireTime;
    }

    public ITaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }

    /**
     * 提供工作中每个任务的处理结果,
     * 上次查询和本次查询之间，可能有多个任务已完成，所以返回List
     */
    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskResultList = new LinkedList<>();
        TaskResult<R> taskResult;
        while ((taskResult=taskDetailQueues.pollFirst()) != null){
            taskResultList.add(taskResult);
        }
        return taskResultList;
    }

    /**
     * 每个任务处理完成后，记录任务的处理结果
     * 因为从业务应用的角度来说，对查询任务进度数据的一致性要不高
     * 我们保证最终一致性即可，无需对整个方法加锁
     */
    public void addTaskResult(TaskResult<R> taskResult){
        // 如果任务执行成功，任务的成功次数加一
        if(TaskResultType.Success.equals(taskResult.getTaskResultType())){
            successCount.incrementAndGet();
        }
        // 已处理任务数量加一
        taskProcessCount.incrementAndGet();
        // 添加任务结果到双向队列
        taskDetailQueues.addLast(taskResult);
        // 如果工作中的任务全部执行完，将工作的结果放入定时缓存，到期后清除
        if(taskProcessCount.get() == jobLength){
            checkJobProcessor.putJob(jobName,expireTime);
        }
    }

    /**
     * 提供工作的整体进度信息
     */
    public String getTotalProcess() {
        return "Success["+successCount.get()+"]/Current["+taskProcessCount.get()
                +"] Total["+jobLength+"]";
    }
}
