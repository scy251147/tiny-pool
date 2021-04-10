package org.tiny.pool.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.tiny.pool.sdk.InstanceRenewl;

/**
 * @author shichaoyang
 * @Description: 连接池
 * @date 2021-04-08 19:35
 */
@Slf4j
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

        public Builder(InstanceRenewl<Connection> newInstance){
            this.newInstance = newInstance;
        }

        private int maxTotal;

        private int maxIdle;

        private int minIdle;

        private int maxWaitMillis;

        private InstanceRenewl<Connection> newInstance;

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
                ConnectionFactory connectionFactory  = new ConnectionFactory(newInstance);
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

    /**
     * 从连接池中获取连接
     * @return
     * @throws Exception
     */
    public Connection borrowConnection() throws Exception {
        return genericObjectPool.borrowObject();
    }

    /**
     * 将连接归还到连接池
     * @param connection
     */
    public void returnConnection(Connection connection) {
        genericObjectPool.returnObject(connection);
    }

    public String monitorConnection(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("idle connection count:").append(genericObjectPool.getNumIdle());
        return stringBuilder.toString();
    }

}
