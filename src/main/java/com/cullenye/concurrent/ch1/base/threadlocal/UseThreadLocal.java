package com.cullenye.concurrent.ch1.base.threadlocal;

public class UseThreadLocal {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return  1;
        }
    };

    public void startThread(){
        Thread[] threads = new Thread[3];
        for(int i=0;i<threads.length;i++)
        {
            threads[i] = new Thread(new MyRunnable(i));
        }

        for(int i=0;i<threads.length;i++)
        {
            threads[i].start();
        }
    }

    private static class MyRunnable implements Runnable{

        int id;
        public MyRunnable(int id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+":start");
            Integer value = threadLocal.get();
            value = value + id;
            threadLocal.set(value);
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
            //threadLaocl.remove();加快回收而已，不使用也自动回收
        }
    }

    public static void main(String[] args) {
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.startThread();
    }
}
