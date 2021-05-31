package com.cullenye.concurrent.ch8a.vo;

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
     * 任务的成功次数
     */
    private AtomicInteger successCount;
    /**
     * 工作中任务目前已经处理的次数
     */
    private AtomicInteger taskProcessCount;
    /**
     * 存放每个任务的处理结果，供查询用
     */
    private LinkedBlockingDeque<TaskResult<R>> taskDetailQueues;
    /**
     * 保留的工作的结果信息供查询的时长
     */
    private final long expireTime;
    /**
     * 处理工作中任务的处理器
     */
    private final ITaskProcesser<?,?> taskProcesser;
}
