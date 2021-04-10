package org.tiny.pool.test;

import org.junit.jupiter.api.Test;
import org.tiny.pool.core.Connection;
import org.tiny.pool.test.biz.NettyConnection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shichaoyang
 * @Description: 不带有连接池的测试
 * @date 2021-04-10 20:13
 */
public class UnPoolTest {

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    @Test
    public void test1() throws Exception {
        Set<Connection> hashCodes = new HashSet<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                Connection connection = new NettyConnection();
                hashCodes.add(connection);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("instance count:" + hashCodes.size() + "  instance list:" + hashCodes);
    }

}
