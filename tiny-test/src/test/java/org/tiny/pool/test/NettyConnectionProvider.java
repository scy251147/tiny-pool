package org.tiny.pool.test;

import org.tiny.pool.core.Connection;

/**
 * @author shichaoyang
 * @Description: netty链接
 * @date 2021-04-08 19:46
 */
public class NettyConnectionProvider implements Connection {

    @Override
    public boolean connect(String host, int port) {
        String instance = "Connection[" + this.hashCode() + "]";
        System.out.println(instance + " The add result: " + true);
        return true;
    }

    @Override
    public void close() {

    }
}
