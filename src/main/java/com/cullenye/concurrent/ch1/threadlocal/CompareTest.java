package com.cullenye.concurrent.ch1.threadlocal;

/**
 * ThreadLocal成员变量和普通成员变量的对比
 * @author yeguanhong
 * @date 2020-09-15 20:07:35
 */
public class CompareTest {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private String name;

    public void set(String str, String name)
    {
        threadLocal.set(str);
        this.name = name;
    }

    public String get(){
        return threadLocal.get();
    }

    public String getName(){
        return name;
    }

    public static void main(String[] args) {
        CompareTest compareTest = new CompareTest();
        new Thread(() -> {
            compareTest.set("hello", "world");
            System.out.println(compareTest.get());
            System.out.println(compareTest.getName());
        }).start();

        new Thread(() -> {
            System.out.println(compareTest.get());
            System.out.println(compareTest.getName());
        }).start();
    }

}
