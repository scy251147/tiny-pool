package org.tiny.pool.core;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author shichaoyang
 * @Description: 连接工厂
 * @date 2021-04-08 19:32
 */
public class ConnectionFactory extends BasePooledObjectFactory<Connection> {

    /**
     * 带参构造
     * @param connection
     */
    public ConnectionFactory(Connection connection){
        this.connection = connection;
    }

    //连接对象
    private Connection connection;

    /**
     * 创建连接实例
     * @return
     * @throws Exception
     */
    @Override
    public Connection create() throws Exception {
        return connection;
    }

    /**
     * 将实例包裹为PooledObject对象
     * @param connection
     * @return
     */
    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject<>(connection);
    }
}
