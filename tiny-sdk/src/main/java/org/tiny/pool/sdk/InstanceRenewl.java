package org.tiny.pool.sdk;


@FunctionalInterface
public interface InstanceRenewl<T> {

    /**
     * 创建connection的实例
     * @return
     */
    T create();

}
