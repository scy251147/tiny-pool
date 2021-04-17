package org.tiny.pool.core;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author shichaoyang
 * @Description: 连接池监控
 * @date 2021-04-17 11:05
 */
public class ConnectionPoolMonitor {

    /**
     * 跟踪连接池信息
     *
     * @param connectionPool
     */
    public static GenericObjectPool<IConnection> tracePool(ConnectionPool connectionPool) {
        GenericObjectPool<IConnection> poolInstance = connectionPool.poolInstance;
        return poolInstance;
    }

    /**
     * 打印出来所有连接实例
     * @param connectionPool
     */
    public static void printAll(ConnectionPool connectionPool){
        System.out.println(connectionPool.poolInstance.listAllObjects());
    }

    /**
     * 打印出来连接池状态
     * @param connectionPool
     */
    public static void traceState(ConnectionPool connectionPool){
        System.out.println("connection active:"+connectionPool.poolInstance.getNumActive());
        System.out.println("connection idle:"+connectionPool.poolInstance.getNumIdle());
    }

}
