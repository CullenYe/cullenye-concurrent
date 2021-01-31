package com.cullenye.concurrent.ch5container.usedelayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列的元素类
 * @author yeguanhong
 * @date 2021-01-31 19:17:03
 */
public class ItemVo<T> implements Delayed {

    /**
     * 到期时间点，单位为纳秒
     */
    private long activeTime;
    /**
     * 泛型数据
     */
    private T data;

    public ItemVo(long activeTime, T data) {

        // 构造函数传入的activeTime是单位为毫秒的到期时长，需要转化成单位为纳秒的到期时间点
        this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime,TimeUnit.MILLISECONDS) + System.nanoTime();
        this.data = data;
    }

    /**
     * 返回元素的剩余时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(activeTime - System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    /**
     * 按照剩余时间排序
     */
    @Override
    public int compareTo(Delayed o) {
        long d = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (d==0)?0:((d>0)?1:-1);
    }

    public T getData(){
        return data;
    }
}
