package com.cullenye.concurrent.ch4lockaqs.readwritelock;

import java.util.Random;

/**
 * 测试类
 * @author yeguanhong
 * @date 2020-09-29 23:39:10
 */
public class Test {

    // 读写线程的比例
    static final int readWriteRatio = 10;
    // 最少写线程数
    static final int minThreadCount = 3;

    /**
     * 读线程
     * @author yeguanhong
     * @date 2020-09-29 23:45:00
     */
    private static class ReadThread extends Thread{

        ProductService productService;

        public ReadThread(ProductService productService){
            this.productService = productService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for(int i=0;i<100;i++){
                productService.getProductInfo();
            }
            System.out.println(Thread.currentThread().getName()+"读取商品数据耗时：" +(System.currentTimeMillis()-start)+"ms");
        }
    }

    /**
     * 写线程
     * @author yeguanhong
     * @date 2020-09-29 23:47:42
     */
    private static class WriteThread extends Thread{

        ProductService productService;

        public WriteThread(ProductService productService){
            this.productService = productService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random random = new Random();
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                productService.setNumber(random.nextInt(5));
            }
            System.out.println(Thread.currentThread().getName()+"======变更商品数据耗时：" +(System.currentTimeMillis()-start)+"ms");
        }
    }

    public static void main(String[] args) {

        // 创建一个ProductInfo实例，然后开启3个写线程，30个读线程，用ProductService接口的方法读写ProductInfo

        ProductInfo productInfo = new ProductInfo(10000, 100000);
        //ProductService productService = new SynchronizedServiceImpl(productInfo);
        ProductService productService = new ReadWriteLockServiceImpl(productInfo);

        for(int i=0;i<minThreadCount;i++){
            new WriteThread(productService).start();
            for(int j=0;j<readWriteRatio;j++){
                new ReadThread(productService).start();
            }
        }
    }
}
