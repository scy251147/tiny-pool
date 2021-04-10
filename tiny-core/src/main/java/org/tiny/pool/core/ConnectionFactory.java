package org.tiny.pool.core;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author shichaoyang
 * @Description: 连接工厂
 * @date 2021-04-08 19:32
 */
public class ConnectionFactory extends BasePooledObjectFactory<Connection> {

    /**
     * 带参构造
     * @param supplier
     */
    public ConnectionFactory(Supplier<Connection> supplier){
        this.supplier = supplier;
    }

    //连接对象
    private Supplier<Connection> supplier;

    /**
     * 创建连接实例
     * @return
     * @throws Exception
     */
    @Override
    public Connection create() throws Exception {
        Connection connection = null;
        if (supplier != null) {
            connection = supplier.get();
        }
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
