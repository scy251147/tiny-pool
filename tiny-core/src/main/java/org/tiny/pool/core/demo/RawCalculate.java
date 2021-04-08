package org.tiny.pool.core.demo;

import com.alibaba.fastjson.JSON;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shichaoyang
 * @Description: 非池化计算方式
 * @date 2020-07-09 20:17
 */
public class RawCalculate {

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public static void main(String... args) throws Exception {
        Set<String> hashCodes = new HashSet<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                String instanceCode = new Calculator().add(1, 3);
                hashCodes.add(instanceCode);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("instance count:" + hashCodes.size() + "  instance list:" + JSON.toJSONString(hashCodes));
    }

}
