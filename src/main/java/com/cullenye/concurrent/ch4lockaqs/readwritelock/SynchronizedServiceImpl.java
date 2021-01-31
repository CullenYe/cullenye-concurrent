package com.cullenye.concurrent.ch4lockaqs.readwritelock;

/**
 * 用内置锁来实现商品服务接口
 * @author yeguanhong
 * @date 2020-09-29 23:28:48
 */
public class SynchronizedServiceImpl implements ProductService{

    private ProductInfo productInfo;

    public SynchronizedServiceImpl(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    @Override
    public synchronized ProductInfo getProductInfo() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productInfo;
    }

    @Override
    public void setNumber(int sellNumber) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        productInfo.changeNumber(sellNumber);
    }
}
