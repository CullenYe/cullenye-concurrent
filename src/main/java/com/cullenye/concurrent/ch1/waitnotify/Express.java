package com.cullenye.concurrent.ch1.waitnotify;

/**
 * 快递实体
 * @author yeguanhong
 * @date 2020-06-16 20:52:55
 */
public class Express {

    // 快递起始位置
    static final String CITY = "ShangHai";

    /**
     * 快递运输里程数
     */
    private int km;
    /**
     * 快递到达的地点
     */
    private String site;

    Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm(){
        this.km = 101;
        notifyAll();
    }

    synchronized void changeSite(){
        this.site = "BeiJing";
        notify();
    }

    synchronized void waitKm(){
        while (this.km <= 100){
            try {
                wait();
                System.out.println("check km thread["+Thread.currentThread().getId()+"] is be notifed.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the km is"+this.km+",I will change db.");
    }

    synchronized void waitSite(){
        while (this.site.equals(CITY)){
            try {
                wait();
                System.out.println("check site thread["+Thread.currentThread().getId()+"] is be notifed.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the site is"+this.site+",I will call user.");
    }
}
