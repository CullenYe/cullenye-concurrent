package com.cullenye.concurrent.ch8a.vo;

/**
 * 方法本身运行是否正确的结果类型
 * @author yeguanhong
 */
public enum TaskResultType {
    // 方法执行完成，业务结果也正确
    Success,
    // 方法执行完成，业务结果错误
    Failure,
    // 方法执行抛出了异常
    Exception
}
