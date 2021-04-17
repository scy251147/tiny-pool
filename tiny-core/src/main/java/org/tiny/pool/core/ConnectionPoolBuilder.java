package org.tiny.pool.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.tiny.pool.sdk.CreateConnection;

/**
 * @author shichaoyang
 * @Description: 连接池builder
 * @date 2021-04-17 09:35
 */
@Slf4j
public class ConnectionPoolBuilder {

    public ConnectionPoolBuilder(CreateConnection<Connection> connectionInstance){
        this.connectionInstance = connectionInstance;
    }

    private ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();

    private CreateConnection<Connection> connectionInstance;

    private GenericObjectPool<Connection> poolInstance;

    public ConnectionPoolBuilder setMaxTotal(int maxTotal){
        connectionPoolConfig.setMaxTotal(maxTotal);
        return this;
    }

    public ConnectionPoolBuilder setMaxIdle(int maxIdle){
        connectionPoolConfig.setMaxIdle(maxIdle);
        return this;
    }

    public ConnectionPoolBuilder setMinIdle(int minIdle){
        connectionPoolConfig.setMinIdle(minIdle);
        return this;
    }

    public ConnectionPoolBuilder setBlockWhenExhausted(boolean blockWhenExhausted){
        connectionPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        return this;
    }

    public ConnectionPoolBuilder setMaxWaitMillis(int maxWaitMillis) {
        connectionPoolConfig.setMaxWaitMillis(maxWaitMillis);
        if (connectionPoolConfig.isBlockWhenExhausted()) {
            if (maxWaitMillis == -1) {
                log.error(" connection will always block when set blockWhenExhausted=true and maxWaitMillis=-1");
            }
        }
        return this;
    }

    public ConnectionPoolBuilder setTestOnBorrow(boolean testOnBorrow) {
        connectionPoolConfig.setTestOnBorrow(testOnBorrow);
        return this;
    }

    public ConnectionPoolBuilder setTestOnReturn(boolean testOnReturn){
        connectionPoolConfig.setTestOnReturn(testOnReturn);
        return this;
    }

    public ConnectionPoolBuilder setTestWhileIdle(boolean testWhileIdle){
        connectionPoolConfig.setTestWhileIdle(testWhileIdle);
        return this;
    }

    public ConnectionPoolBuilder setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis){
        connectionPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        return this;
    }

    public ConnectionPoolBuilder setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis){
        connectionPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return this;
    }

    public GenericObjectPool<Connection> getPoolInstance(){
        return this.poolInstance;
    }

    public ConnectionPool build() {
        try {

            //连接实例
            ConnectionFactory connectionFactory = new ConnectionFactory(connectionInstance);

            //池化配置
            GenericObjectPoolConfig<Connection> genericObjectPoolConfig = new GenericObjectPoolConfig();
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
            poolInstance = new GenericObjectPool<Connection>(connectionFactory, genericObjectPoolConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ConnectionPool(this);
    }

}
