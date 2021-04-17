package org.tiny.pool.test;

import org.junit.jupiter.api.Test;
import org.tiny.pool.core.ConnectionPool;
import org.tiny.pool.core.Connection;
import org.tiny.pool.core.ConnectionPoolConfig;
import org.tiny.pool.test.biz.NettyConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shichaoyang
 * @Description: 带有连接池的测试
 * @date 2021-04-07 20:13
 */
public class InPoolTest {

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    @Test
    public void test1() throws InterruptedException {
        //池化实例
        ConnectionPool connectionPool =  ConnectionPool.Builder(()->new NettyConnection())
                .setMaxTotal(2)
                .setMaxIdle(2)
                .setMinIdle(0)
                .build();

        Set<Connection> hashCodes = new HashSet<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    //从池子中拿出实例
                    Connection connection = connectionPool.borrowConnection();
                    connection.connect("127.0.0.1", 6379);
                    //使用完毕，将实例归还池子
                    connectionPool.returnConnection(connection);
                    hashCodes.add(connection);
                    countDownLatch.countDown();
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
        System.out.println("instance count:" + hashCodes.size() + "  instance list:" + hashCodes);

        System.out.println(connectionPool.monitorConnection());
    }

}
