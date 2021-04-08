package org.tiny.pool.core;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.ServiceLoader;

/**
 * @author shichaoyang
 * @Description: 连接池
 * @date 2021-04-08 19:35
 */
public class ConnectionPoolBuilder {

    private int maxTotal;

    private int maxIdle;

    private int minIdle;

    private int maxWaitMillis;

    private GenericObjectPool<Connection> connectionPool;

    public ConnectionPoolBuilder setMaxTotal(int maxTotal){
        this.maxTotal = maxTotal;
        return this;
    }

    public ConnectionPoolBuilder setMaxIdle(int maxIdle){
        this.maxIdle = maxIdle;
        return this;
    }

    public ConnectionPoolBuilder setMinIdle(int minIdle){
        this.minIdle = minIdle;
        return this;
    }

    public ConnectionPoolBuilder setMaxWaitMillis(int maxWaitMillis){
        this.maxWaitMillis = maxWaitMillis;
        return this;
    }

    public ConnectionPoolBuilder build() {
        try {
            //连接实例
            ServiceLoader<Connection> serviceLoader = ServiceLoader.load(Connection.class);
            ConnectionFactory connectionFactory = null;
            for (Connection connection : serviceLoader) {
                connectionFactory = new ConnectionFactory(connection);
            }
            //池化配置
            GenericObjectPoolConfig<Connection> connectionPoolConfig = new GenericObjectPoolConfig();
            if (this.maxTotal != 0) {
                connectionPoolConfig.setMaxTotal(this.maxTotal);
            }
            if (this.maxWaitMillis != 0) {
                connectionPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
            }
            //创建连接池
            connectionPool = new GenericObjectPool<>(connectionFactory, connectionPoolConfig);
        }catch(Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public Connection borrowConnection() throws Exception {
        return connectionPool.borrowObject();
    }

    public void returnConnection(Connection connection) {
        connectionPool.returnObject(connection);
    }

}
