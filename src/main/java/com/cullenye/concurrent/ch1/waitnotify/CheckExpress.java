package com.cullenye.concurrent.ch1.waitnotify;

/**
 * 检查快递的线程
 * @author yeguanhong
 * @date 2020-09-15 20:08:17
 */
public class CheckExpress {

    private static Express express = new Express(100,Express.CITY);

    /**
     * 检查里程数变化的线程,不满足条件，线程一直等待
     */
    private static class CheckKm extends Thread{
        @Override
        public void run() {
            express.waitKm();
        }
    }

    /**
     * 检查地点变化的线程,不满足条件，线程一直等待
     */
    private static class CheckSite extends Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //开启三个检查地点的线程
        for(int i=0;i<3;i++){
            new CheckSite().start();
        }
        //开启三个检查里程数的线程
        for(int i=0;i<3;i++){
            new CheckKm().start();
        }

        Thread.sleep(1000);

        //express.changeKm();
        express.changeSite();
    }
}
