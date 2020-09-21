package com.cullenye.concurrent.ch1base.base;

/**
 * 使用join
 * @author yeguanhong
 * @date 2020-09-15 19:29:21
 */
public class UseJoin {

    private static class JumpQeueue implements Runnable{

        private Thread thread;

        public JumpQeueue(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {

                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"线程结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread preThread = Thread.currentThread();
        for(int i=0;i<10;i++)
        {
            Thread thread = new Thread(new JumpQeueue(preThread),String .valueOf(i));
            System.out.println(thread.getName()+"线程启动，让线程"+preThread.getName()+"插队");
            thread.start();
            preThread = thread;
        }

        Thread.sleep(2000);
        System.out.println("线程"+Thread.currentThread().getName() +"结束");
    }
}
