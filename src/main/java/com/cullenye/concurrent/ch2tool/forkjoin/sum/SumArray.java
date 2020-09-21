package com.cullenye.concurrent.ch2tool.forkjoin.sum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用ForkJoinPool计算
 * @author yeguanhong
 * @date 2020-06-16 23:20:14
 */
public class SumArray {

    private static class SumTask extends RecursiveTask<Integer>{

        // 计算的最小区间
        private static final int THREADHOLDER = MakeArray.LENGTH/10;
        // 表示我们要统计的数组
        private int[] array;
        // 统计开始的下标
        private int start;
        // 统计结束的下标
        private int end;

        SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if((end-start)<THREADHOLDER)
            {
                int sum = 0;
                for(int i=start;i<=end;i++)
                {
                    sum += array[i];
                }
                return sum;
            }
            else{
                int mid = (start+end)/2;
                SumTask left = new SumTask(array,start,mid);
                SumTask right = new SumTask(array,mid+1,end);
                // invokeAll提交其它的ForkJoinTask进行执行，并等待Task完成，是同步方法
                invokeAll(left,right);
                // join的返回类型和compute方法相同
                return left.join()+right.join();
            }
        }
    }

    public static void main(String[] args) {
        int[] array = MakeArray.getArray();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask sumTask = new SumTask(array,0,array.length-1);
        System.out.println("要调用invoke");
        long start = System.currentTimeMillis();
        //invoke是同步方法，提交任务给框架执行，并等待任务完成
        forkJoinPool.invoke(sumTask);
        System.out.println("调用完invoke");
        //join是同步方法，作用是阻塞当前线程，并等待返回结果
        System.out.println("结果："+sumTask.join());
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");

    }
}
