package com.cullenye.concurrent.cha4lockaqs.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 快递实体类
 * @author yeguanhong
 * @date 2020-09-30 00:23:00
 */
public class ExpressCondition {

    public final static String CITY = "ShangHai";
    // 快递运输里程数
    private int km;
    // 快递到达地点
    private String site;

    private Lock lock = new ReentrantLock();
    private Condition kmCondition = lock.newCondition();
    private Condition siteCondition = lock.newCondition();

    public ExpressCondition(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /**
     * 变化公里数，并通知处于wait状态的全部线程，进行业务处理
     * @author yeguanhong
     * @date 2020-09-30 00:26:24
     */
    public void changeKm(){
        lock.lock();
        try{
            this.km = 101;
            kmCondition.signal();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 变化地点，并通知处于wait状态的某一个线程，进行业务处理
     * @author yeguanhong
     * @date 2020-09-30 00:28:15
     */
    public void changeSite(){
        lock.lock();
        try{
            this.site = "BeiJing";
            siteCondition.signal();
        }finally {
            lock.unlock();
        }
    }

    public void awaitKm(){
        lock.lock();
        try{
            while (km <= 100){
                try {
                    kmCondition.await();
                    System.out.println("waitKm被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("里程改为" + km);
        }finally {
            lock.unlock();
        }
    }

    public void awaitSite(){
        lock.lock();
        try{
            while (CITY.equals(site)) {
                try {
                    siteCondition.await();
                    System.out.println("waitSite被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("城市改为" + site);
        }finally {
            lock.unlock();
        }
    }
}
