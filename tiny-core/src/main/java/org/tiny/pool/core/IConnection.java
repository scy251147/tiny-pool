package org.tiny.pool.core;

/**
 * @author shichaoyang
 * @Description: 连接
 * @date 2021-04-08 19:25
 */
public interface IConnection {

    /**
     * 连接远端
     * @param host
     * @param port
     */
    boolean connect(String host, int port);

    /**
     * 关闭链接
     */
    void close();

}
