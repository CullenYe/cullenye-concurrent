package com.cullenye.concurrent.ch8a.vo;

/**
 * 任务处理后返回的结果实体类
 * @author yeguanhong
 */
public class TaskResult<R> {

    /**
     * 方法执行结果
     */
    private final TaskResultType taskResultType;
    /**
     * 方法执行后的结果数据
     */
    private final R returnValue;
    /**
     * 如果方法失败，这里可以填充原因
     */
    private final String reason;

    public TaskResult(TaskResultType taskResultType, R returnValue, String reason) {
        this.taskResultType = taskResultType;
        this.returnValue = returnValue;
        this.reason = reason;
    }

    public TaskResult(TaskResultType taskResultType, R returnValue) {
        this.taskResultType = taskResultType;
        this.returnValue = returnValue;
        this.reason = "Success";
    }

    public TaskResultType getTaskResultType() {
        return taskResultType;
    }

    public R getReturnValue() {
        return returnValue;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "TaskResult [resultType=" + taskResultType
                + ", returnValue=" + returnValue
                + ", reason=" + reason + "]";
    }
}
