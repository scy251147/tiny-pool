package org.tiny.pool.core;

/**
 * @author shichaoyang
 * @Description: 连接池接口
 * @date 2021-04-17 12:35
 */
public interface IConnectionPool {

    /**
     * 从连接池中获取连接
     *
     * @return
     * @throws Exception
     */
    IConnection borrowConnection();

    /**
     * 将连接归还到连接池
     *
     * @param connection
     */
    void returnConnection(IConnection connection);

    /**
     * 作废连接并从连接池移除
     *
     * @param connection
     */
    void invalidateConnection(IConnection connection);

    /**
     * 扫描连接池，将无效连接驱逐
     */
    void evictConnection();

    /**
     * 清空连接池
     */
    void clearPool();
}
