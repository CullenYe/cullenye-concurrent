package com.cullenye.concurrent.ch7safe.dcl;

/**
 * 在域上运用延迟初始化占位类模式
 */
public class InstanceLazy {

    private Integer value;
    /**
     * 成员变量很耗资源
     */
    private Integer heavy;

    private InstanceLazy(Integer value){
        this.value = value;
    }

    private static class InstanceHolder{
        public static Integer val = new Integer(100);
    }

    public Integer getValue() {
        return value;
    }

    public Integer getVal() {
        return InstanceHolder.val;
    }
}
