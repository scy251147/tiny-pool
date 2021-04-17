package org.tiny.pool.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.tiny.pool.sdk.CreateConnection;

/**
 * @author shichaoyang
 * @Description: 连接池
 * @date 2021-04-08 19:35
 */
@Slf4j
public class ConnectionPool {

    /**
     * 带参构造
     * @param builder
     */
    public ConnectionPool(ConnectionPoolBuilder builder){
        this.poolInstance = builder.getPoolInstance();
    }

    //连接池实例
    protected GenericObjectPool<Connection> poolInstance;

    /**
     * 连接池构建
     * @param newInstance
     * @return
     */
    public static ConnectionPoolBuilder Builder(CreateConnection<Connection> newInstance) {
        return new ConnectionPoolBuilder(newInstance);
    }

    /**
     * 从连接池中获取连接
     * @return
     * @throws Exception
     */
    public Connection borrowConnection() {
        Connection connection = null;
        try {
            connection = poolInstance.borrowObject();
        } catch (Exception e) {
            log.error("borrow connection from connection pool error", e);
        }
        return connection;
    }

    /**
     * 将连接归还到连接池
     * @param connection
     */
    public void returnConnection(Connection connection) {
        poolInstance.returnObject(connection);
    }

    /**
     * 作废连接并从连接池移除
     * @param connection
     */
    public void invalidateConnection(Connection connection) {
        try {
            poolInstance.invalidateObject(connection);
        } catch (Exception e) {
            log.error("invalidate connection from connection pool error", e);
        }
    }

    /**
     * 扫描连接池，将无效连接驱逐
     */
    public void evictConnection() {
        try {
            poolInstance.evict();
        } catch (Exception e) {
            log.error("evict connection from connection pool error", e);
        }
    }

    /**
     * 清空连接池
     */
    public void clearPool(){
        poolInstance.clear();
    }
}
