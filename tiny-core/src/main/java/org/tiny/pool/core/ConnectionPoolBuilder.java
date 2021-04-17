package org.tiny.pool.core;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.tiny.pool.sdk.CreateConnection;

public class ConnectionPoolBuilder {

    public ConnectionPoolBuilder(CreateConnection<Connection> connectionInstance){
        this.connectionInstance = connectionInstance;
    }

    private int maxTotal;

    private int maxIdle;

    private int minIdle;

    private int maxWaitMillis;

    private CreateConnection<Connection> connectionInstance;

    private GenericObjectPool<Connection> poolInstance;

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

    public GenericObjectPool<Connection> getPoolInstance(){
        return this.poolInstance;
    }

    public ConnectionPool build() {
        try {

            //连接实例
            ConnectionFactory connectionFactory  = new ConnectionFactory(connectionInstance);

            //池化配置
            GenericObjectPoolConfig<Connection> connectionPoolConfig = new GenericObjectPoolConfig();

            if (this.maxTotal != 0) {
                connectionPoolConfig.setMaxTotal(this.maxTotal);
            }
            if (this.maxWaitMillis != 0) {
                connectionPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
            }
            //创建连接池
            poolInstance = new GenericObjectPool<>(connectionFactory, connectionPoolConfig);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ConnectionPool(this);
    }

}
