package com.cullenye.concurrent.ch4lockaqs.readwritelock;

/**
 * 商品实体类
 * @author yeguanhong
 * @date 2020-09-29 23:18:09
 */
public class ProductInfo {

    // 销售总额
    private double totalMoney;
    // 库存数
    private int storeNumber;

    public ProductInfo(double totalMoney, int storeNumber) {
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    public void changeNumber(int sellNumber){
        totalMoney += sellNumber * 25;
        storeNumber -= sellNumber;
    }
}
