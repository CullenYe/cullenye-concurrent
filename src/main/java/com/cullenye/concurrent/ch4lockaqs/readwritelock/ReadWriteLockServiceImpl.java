package com.cullenye.concurrent.ch4lockaqs.readwritelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用读写锁来实现商品服务接口
 * @author yeguanhong
 * @date 2020-09-29 23:31:18
 */
public class ReadWriteLockServiceImpl implements ProductService{

    private ProductInfo productInfo;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    public ReadWriteLockServiceImpl(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    @Override
    public ProductInfo getProductInfo() {
        readLock.lock();
        try{
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return productInfo;
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public void setNumber(int sellNumber) {
        writeLock.lock();
        try{
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            productInfo.changeNumber(sellNumber);
        }finally {
            writeLock.unlock();
        }
    }
}
