package org.tiny.pool.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.tiny.pool.core.exception.TpPoolCreateErrorException;
import org.tiny.pool.sdk.CreateConnection;

/**
 * @author shichaoyang
 * @Description: 连接池builder
 * @date 2021-04-17 09:35
 */
@Slf4j
public class ConnectionPoolBuilder {

    /**
     * 带参构造
     * @param connectionInstance
     */
    public ConnectionPoolBuilder(CreateConnection<IConnection> connectionInstance){
        this.connectionInstance = connectionInstance;
    }

    //自定义连接池配置
    private ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();

    //连接池中的连接实例创建方式
    private CreateConnection<IConnection> connectionInstance;

    //连接池实例
    private GenericObjectPool<IConnection> poolInstance;

    /**
     * 设置连接池中最大的连接数
     * @param maxTotal
     * @return
     */
    public ConnectionPoolBuilder setMaxTotal(int maxTotal){
        connectionPoolConfig.setMaxTotal(maxTotal);
        return this;
    }

    /**
     * 设置连接池中允许最大空闲的连接数
     * @param maxIdle
     * @return
     */
    public ConnectionPoolBuilder setMaxIdle(int maxIdle){
        connectionPoolConfig.setMaxIdle(maxIdle);
        return this;
    }

    /**
     * 设置连接池中确保最少空闲的连接数
     * @param minIdle
     * @return
     */
    public ConnectionPoolBuilder setMinIdle(int minIdle){
        connectionPoolConfig.setMinIdle(minIdle);
        return this;
    }

    /**
     * 设置连接池耗尽，调用者是否需要等待
     * @param blockWhenExhausted
     * @return
     */
    public ConnectionPoolBuilder setBlockWhenExhausted(boolean blockWhenExhausted){
        connectionPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        return this;
    }

    /**
     * 设置连接池耗尽，调用者的最大等待时间
     * @param maxWaitMillis
     * @return
     */
    public ConnectionPoolBuilder setMaxWaitMillis(int maxWaitMillis) {
        connectionPoolConfig.setMaxWaitMillis(maxWaitMillis);
        if (connectionPoolConfig.isBlockWhenExhausted()) {
            if (maxWaitMillis == -1) {
                log.error(" connection will always block when set blockWhenExhausted=true and maxWaitMillis=-1");
            }
        }
        return this;
    }

    /**
     * 设置从连接池借用连接的时候，是否做有效性检测
     * @param testOnBorrow
     * @return
     */
    public ConnectionPoolBuilder setTestOnBorrow(boolean testOnBorrow) {
        connectionPoolConfig.setTestOnBorrow(testOnBorrow);
        return this;
    }

    /**
     * 设置向连接池归还连接的时候，是否做有效性检测
     * @param testOnReturn
     * @return
     */
    public ConnectionPoolBuilder setTestOnReturn(boolean testOnReturn){
        connectionPoolConfig.setTestOnReturn(testOnReturn);
        return this;
    }

    /**
     * 设置用一个专门的线程对空闲的连接进行有效性检测
     * @param testWhileIdle
     * @return
     */
    public ConnectionPoolBuilder setTestWhileIdle(boolean testWhileIdle){
        connectionPoolConfig.setTestWhileIdle(testWhileIdle);
        return this;
    }

    /**
     * 设置两次空闲连接扫描的活动之间，要睡眠的毫秒数
     * @param timeBetweenEvictionRunsMillis
     * @return
     */
    public ConnectionPoolBuilder setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis){
        connectionPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        return this;
    }

    /**
     * 设置一个连接至少停留在空闲状态的最短时间，然后才能被空闲连接扫描线程进行有效性检测
     * @param minEvictableIdleTimeMillis
     * @return
     */
    public ConnectionPoolBuilder setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis){
        connectionPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return this;
    }

    /**
     * 获取连接池实例
     * @return
     */
    public GenericObjectPool<IConnection> getPoolInstance(){
        return this.poolInstance;
    }

    /**
     * 根据配置构建连接池
     * @return
     */
    public ConnectionPool build() throws TpPoolCreateErrorException {
        try {

            //连接实例
            ConnectionFactory connectionFactory = new ConnectionFactory(connectionInstance);

            //池化配置
            GenericObjectPoolConfig<IConnection> genericObjectPoolConfig = new GenericObjectPoolConfig();
            genericObjectPoolConfig.setMaxTotal(connectionPoolConfig.getMaxTotal());
            genericObjectPoolConfig.setMaxIdle(connectionPoolConfig.getMaxIdle());
            genericObjectPoolConfig.setMinIdle(connectionPoolConfig.getMinIdle());
            genericObjectPoolConfig.setBlockWhenExhausted(connectionPoolConfig.isBlockWhenExhausted());
            genericObjectPoolConfig.setMaxWaitMillis(connectionPoolConfig.getMaxWaitMillis());
            genericObjectPoolConfig.setTestOnBorrow(connectionPoolConfig.isTestOnBorrow());
            genericObjectPoolConfig.setTestOnReturn(connectionPoolConfig.isTestOnReturn());
            genericObjectPoolConfig.setTestWhileIdle(connectionPoolConfig.isTestWhileIdle());
            genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(connectionPoolConfig.getTimeBetweenEvictionRunsMillis());
            genericObjectPoolConfig.setMinEvictableIdleTimeMillis(connectionPoolConfig.getMinEvictableIdleTimeMillis());

            //创建连接池
            poolInstance = new GenericObjectPool<IConnection>(connectionFactory, genericObjectPoolConfig);
        } catch (Exception e) {
            log.error("create connection pool error", e);
            throw new TpPoolCreateErrorException("create connection pool error");
        }
        return new ConnectionPool(this);
    }

}
