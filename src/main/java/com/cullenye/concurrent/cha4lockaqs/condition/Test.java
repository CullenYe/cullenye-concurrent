package com.cullenye.concurrent.cha4lockaqs.condition;

/**
 * 测试类
 * @author yeguanhong
 * @date 2020-09-30 00:32:20
 */
public class Test {

    /**
     * 监控里程数的线程
     * @author yeguanhong
     * @date 2020-09-30 00:35:44
     */
    private static class CheckKmThread extends Thread{

        ExpressCondition expressCondition;

        public CheckKmThread(ExpressCondition expressCondition) {
            this.expressCondition = expressCondition;
        }

        @Override
        public void run() {
            expressCondition.awaitKm();
            System.out.println(Thread.currentThread().getName()+"被唤醒，将记录km");
        }
    }

    /**
     * 监控地点的线程
     * @author yeguanhong
     * @date 2020-09-30 00:36:40
     */
    private static class CheckSiteThread extends Thread{

        ExpressCondition expressCondition;

        public CheckSiteThread(ExpressCondition expressCondition) {
            this.expressCondition = expressCondition;
        }

        @Override
        public void run() {
            expressCondition.awaitSite();
            System.out.println(Thread.currentThread().getName()+"被唤醒，将记录site");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ExpressCondition expressCondition = new ExpressCondition(99, ExpressCondition.CITY);
        for(int i=0;i<3;i++) {
            new CheckSiteThread(expressCondition).start();
        }

        for(int i=0;i<3;i++) {
            new CheckKmThread(expressCondition).start();
        }

        Thread.sleep(1000);

        expressCondition.changeKm();
        expressCondition.changeSite();
    }

}
