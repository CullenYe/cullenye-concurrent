package com.cullenye.concurrent.ch5container.usedelayqueue;

import java.util.concurrent.DelayQueue;

/**
 * 获取到期订单的线程
 * @author yeguanhong
 * @date 2021-01-31 19:47:45
 */
public class GetThread implements Runnable {

    private DelayQueue<ItemVo<Order>> delayQueue = new DelayQueue<>();

    public GetThread(DelayQueue<ItemVo<Order>> delayQueue){
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        while (true){
            ItemVo<Order> orderItem = null;
            try {
                orderItem = delayQueue.take();
                Order order = orderItem.getData();
                System.out.println("取得了"+order.getOrderNo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
