package com.cullenye.concurrent.ch5container.usedelayqueue;

/**
 * 封装的订单实体
 * @author yeguanhong
 * @date 2021-01-31 19:35:54
 */
public class Order {

    /**
     * 订单编号
     */
    private final String orderNo;
    /**
     * 订单金额
     */
    private final double orderMoney;

    public Order(String orderNo, double orderMoney) {
        this.orderNo = orderNo;
        this.orderMoney = orderMoney;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public double getOrderMoney() {
        return orderMoney;
    }
}
