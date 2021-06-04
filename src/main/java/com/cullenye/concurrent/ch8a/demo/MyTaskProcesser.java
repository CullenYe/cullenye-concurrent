package com.cullenye.concurrent.ch8a.demo;

import com.cullenye.concurrent.ch8a.vo.ITaskProcesser;
import com.cullenye.concurrent.ch8a.vo.TaskResult;
import com.cullenye.concurrent.ch8a.vo.TaskResultType;

import java.util.Random;

/**
 * 一个实际任务类，将数值加上一个随机数，并休眠随机时间
 * @author yeguanhong
 */
public class MyTaskProcesser implements ITaskProcesser<Integer,Integer> {

    @Override
    public TaskResult<Integer> taskExecute(Integer data) {
        Random random = new Random();
        int flag = random.nextInt(500);
        try {
            Thread.sleep(flag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(flag<=300){
            // 正常处理
            Integer returnValue = data + flag;
            return new TaskResult<>(TaskResultType.Success,returnValue);
        }else if(flag<400){
            // 处理失败
            return new TaskResult<>(TaskResultType.Failure,-1,"Failure");
        }else {
            // 发生异常
            try {
                throw new RuntimeException("执行任务发生过异常");
            } catch (RuntimeException e) {
                return new TaskResult<>(TaskResultType.Exception,-1,e.getMessage());
            }
        }
    }
}
