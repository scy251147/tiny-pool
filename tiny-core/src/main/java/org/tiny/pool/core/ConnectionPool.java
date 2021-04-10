package org.tiny.pool.core;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.function.Supplier;

/**
 * @author shichaoyang
 * @Description: 连接池
 * @date 2021-04-08 19:35
 */
public class ConnectionPool {

    private ConnectionPool(Builder builder){
        this.maxTotal = builder.maxTotal;
        this.maxIdle = builder.maxIdle;
        this.minIdle = builder.minIdle;
        this.maxWaitMillis = builder.maxWaitMillis;
        this.genericObjectPool = builder.genericObjectPool;
    }

    private int maxTotal;

    private int maxIdle;

    private int minIdle;

    private int maxWaitMillis;

    private GenericObjectPool<Connection> genericObjectPool;

    public static class Builder{

        public Builder(Supplier<Connection> supplier){
            this.supplier = supplier;
        }

        private int maxTotal;

        private int maxIdle;

        private int minIdle;

        private int maxWaitMillis;

        private Supplier<Connection> supplier;

        private GenericObjectPool<Connection> genericObjectPool;

        public Builder setMaxTotal(int maxTotal){
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setMaxIdle(int maxIdle){
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMinIdle(int minIdle){
            this.minIdle = minIdle;
            return this;
        }

        public Builder setMaxWaitMillis(int maxWaitMillis){
            this.maxWaitMillis = maxWaitMillis;
            return this;
        }

        public ConnectionPool build() {
            try {
                //连接实例
                ConnectionFactory connectionFactory  = new ConnectionFactory(supplier);
                //池化配置
                GenericObjectPoolConfig<Connection> connectionPoolConfig = new GenericObjectPoolConfig();
                if (this.maxTotal != 0) {
                    connectionPoolConfig.setMaxTotal(this.maxTotal);
                }
                if (this.maxWaitMillis != 0) {
                    connectionPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
                }
                //创建连接池
                genericObjectPool = new GenericObjectPool<>(connectionFactory, connectionPoolConfig);
            }catch(Exception e){
                e.printStackTrace();
            }
            return new ConnectionPool(this);
        }

    }

    public Connection borrowConnection() throws Exception {
        return genericObjectPool.borrowObject();
    }

    public void returnConnection(Connection connection) {
        genericObjectPool.returnObject(connection);
    }

}
