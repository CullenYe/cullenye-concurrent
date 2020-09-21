package com.cullenye.concurrent.ch3cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用AtomicReference
 * @author yeguanhong
 * @date 2020-09-21 23:32:35
 */
public class UseAtomicReference {

    private static final AtomicReference<UserInfo> atomicReference = new AtomicReference<>();

    static class UserInfo {
        private final String name;
        private final int age;
        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }
    }

    public static void main(String[] args) {

        // 原子类设置初始值
        UserInfo userInfo = new UserInfo("Rose", 17);
        atomicReference.set(userInfo);

        // 原子操作更新原子类的值
        UserInfo updateUserInfo = new UserInfo("Jack", 18);
        atomicReference.compareAndSet(userInfo,updateUserInfo);

        // 不是更新对象本身，所以输出原来的值
        System.out.println(userInfo.getName());
        System.out.println(userInfo.getAge());

        System.out.println(atomicReference.get().getName());
        System.out.println(atomicReference.get().getAge());
    }

}
