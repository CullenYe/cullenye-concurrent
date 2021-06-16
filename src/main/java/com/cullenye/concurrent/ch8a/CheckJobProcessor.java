package com.cullenye.concurrent.ch8a;

import com.cullenye.concurrent.ch5container.usedelayqueue.ItemVo;
import com.cullenye.concurrent.ch8a.vo.JobInfo;

import java.util.Map;
import java.util.concurrent.DelayQueue;

/**
 * 任务完成后,在一定的时间供查询结果，过期需要清理，释放内存
 * 使用DelayQueue存储完成的任务，过期后即清理
 * @author yeguanhong
 */
public class CheckJobProcessor {

    /**
     * 存放任务的队列
     */
    private static DelayQueue<ItemVo<String>> queue = new DelayQueue<>();

    /**
     * 单例化
     */
    private static class ProcessorHolder{
        private static CheckJobProcessor checkJobProcessor = new CheckJobProcessor();
    }

    public static CheckJobProcessor getInstance(){
        return ProcessorHolder.checkJobProcessor;
    }

    /**
     * 任务完成后，放入队列，经过expireTime时间后，会从整个框架中移除
     */
    public static void putJob(String jobName,long expireTime){
        ItemVo<String> itemVo = new ItemVo<>(expireTime,jobName);
        queue.offer(itemVo);
        System.out.println(jobName+"已经放入过期检查缓存，时长："+expireTime);
    }

    /**
     * 处理队列中到期任务的线程
     */
    private static class FetchJob implements Runnable{

        private static DelayQueue<ItemVo<String>> queue = CheckJobProcessor.queue;
        private static Map<String, JobInfo<?>> jobInfoMap = PendingJobPool.getJobMap();

        @Override
        public void run() {
            while (true){
                try {
                    // 只有能take到元素，则说明已经到期，需要清除
                    ItemVo<String> itemVo = queue.take();
                    String jobName = itemVo.getData();
                    jobInfoMap.remove(jobName);
                    System.out.println(jobName+" 过期了，从缓存中清除");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
      开启处理队列中到期任务的线程
     */
    static {
        Thread thread = new Thread(new FetchJob());
        // 使用守护线程，当住线程停止后，该线程也停止
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启过期检查的守护线程......");
    }

}
