package com.cullenye.concurrent.ch8a.vo;

/**
 * 要求框架使用者实现的任务接口，因为任务的性质在调用时才知道，
 * 所以传入的参数和方法的返回值均使用泛型
 * @author yeguanhong
 */
public interface ITaskProcesser<T,R> {
    TaskResult<R> taskExecute(T data);
}
