package com.cullenye.concurrent.ch5container.usedelayqueue;

import java.util.concurrent.DelayQueue;

/**
 * 放入订单的线程,负责放入两个不同过期时间的订单
 * @author yeguanhong
 * @date 2021-01-31 19:37:44
 */
public class PutThread implements Runnable{

    private DelayQueue<ItemVo<Order>> delayQueue = new DelayQueue<>();

    public PutThread(DelayQueue<ItemVo<Order>> delayQueue){
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        Order order = new Order("1001", 366);
        ItemVo<Order> orderItem = new ItemVo<Order>(5000, order);
        delayQueue.offer(orderItem);
        System.out.println("订单5秒后到期："+order.getOrderNo());

        Order order2 = new Order("1002", 366);
        ItemVo<Order> orderItem2 = new ItemVo<Order>(8000, order2);
        delayQueue.offer(orderItem2);
        System.out.println("订单8秒后到期："+order2.getOrderNo());
    }

}
