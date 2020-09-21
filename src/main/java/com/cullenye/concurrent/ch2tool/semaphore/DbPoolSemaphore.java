package com.cullenye.concurrent.ch2tool.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * PoolSemaphore实现的数据库连接池
 * @author yeguanhong
 * @date 2020-09-15 19:31:12
 */
public class DbPoolSemaphore {

    /**
     * 线程数
     */
    private static int POOL_SIZE = 10;

    /**
     * useful表示可用的数据库连接，useless表示已用的数据库连接
     */
    private final Semaphore useful,useless;

    /**
     * 存放数据库连接的容器
     */
    private static LinkedList<Connection> linkedList = new LinkedList<>();

    public DbPoolSemaphore() {
        this.useful = new Semaphore(POOL_SIZE);
        this.useless = new Semaphore(0);
    }

    static {
        // 初始化数据库连接池
        for(int i=0;i<POOL_SIZE;i++){
            linkedList.add(SqlConnectImpl.fetchConnection());
        }
    }

    public Connection getConnection() throws InterruptedException{
        useful.acquire();
        Connection connection = null;
        synchronized (linkedList){
            connection = linkedList.removeFirst();
        }
        useless.release();
        return connection;
    }

    public void releaseConnection(Connection connection) throws InterruptedException{
        if(connection != null){
            System.out.println("当前有"+useful.getQueueLength()+"个线程等待数据库连接，"+"可用连接数:"+useful.availablePermits());
            useless.acquire();
            synchronized (linkedList){
                linkedList.add(connection);
            }
            useful.release();
        }
    }
}
