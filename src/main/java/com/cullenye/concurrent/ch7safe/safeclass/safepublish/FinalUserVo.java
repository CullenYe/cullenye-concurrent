package com.cullenye.concurrent.ch7safe.safeclass.safepublish;

/**
 * final声明的UserVo
 */
public final class FinalUserVo {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
