package org.tiny.pool.core;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.tiny.pool.core.exception.TpInstanceNotCreateException;
import org.tiny.pool.sdk.InstanceRenewl;

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
     * @param newInstance
     */
    public ConnectionFactory(InstanceRenewl<Connection> newInstance){
        this.newInstance = newInstance;
    }

    //连接对象
    private InstanceRenewl<Connection> newInstance;

    /**
     * 创建连接实例
     * @return
     * @throws Exception
     */
    @Override
    public Connection create() throws Exception {
        Connection connection = null;
        if (newInstance != null) {
            connection = newInstance.create();
        } else {
            throw new TpInstanceNotCreateException("connection instance creation should not be null");
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
