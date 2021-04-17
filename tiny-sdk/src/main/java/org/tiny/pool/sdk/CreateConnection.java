package org.tiny.pool.sdk;


@FunctionalInterface
public interface CreateConnection<T> {

    /**
     * 创建connection的实例
     * @return
     */
    T create();

}
