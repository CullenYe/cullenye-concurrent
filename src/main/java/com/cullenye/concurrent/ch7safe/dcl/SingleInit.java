package com.cullenye.concurrent.ch7safe.dcl;

/**
 * 懒汉式-延迟初始化占位类模式
 */
public class SingleInit {

    private SingleInit(){}

    // 定义一个私有类 ，来持有当前类的实例
    private static class InstanceHolder{
        private static SingleInit instance = new SingleInit();
    }

    // 当获取对象时，才通过内部类InstanceHolder创建实例
    public static SingleInit getInstance(){
        return InstanceHolder.instance;
    }
}
