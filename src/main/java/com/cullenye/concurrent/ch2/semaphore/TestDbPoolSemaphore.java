package com.cullenye.concurrent.ch2.semaphore;

import java.sql.Connection;
import java.util.Random;

/**
 * 数据库连接池测试类
 * @author yeguanhong
 * @date 2020-09-15 20:04:17
 */
public class TestDbPoolSemaphore {

    private static DbPoolSemaphore dbPoolSemaphore = new DbPoolSemaphore();

    private static class Business implements Runnable{

        @Override
        public void run() {
            Random random = new Random();
            Connection connection = null;
            try {
                connection = dbPoolSemaphore.getConnection();
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    dbPoolSemaphore.releaseConnection(connection);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<50;i++){
            new Thread(new Business()).start();
        }
    }
}
