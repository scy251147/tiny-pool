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

    public ConnectionPool(ConnectionPoolBuilder builder){
        this.poolInstance = builder.getPoolInstance();
    }

    private GenericObjectPool<Connection> poolInstance;

    /**
     * 从连接池中获取连接
     * @return
     * @throws Exception
     */
    public Connection borrowConnection() throws Exception {
        return poolInstance.borrowObject();
    }

    /**
     * 将连接归还到连接池
     * @param connection
     */
    public void returnConnection(Connection connection) {
        poolInstance.returnObject(connection);
    }

    public String monitorConnection(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("idle connection count:").append(poolInstance.getNumIdle());
        return stringBuilder.toString();
    }

    public static ConnectionPoolBuilder Builder(CreateConnection<Connection> newInstance) {
        return new ConnectionPoolBuilder(newInstance);
    }

}
