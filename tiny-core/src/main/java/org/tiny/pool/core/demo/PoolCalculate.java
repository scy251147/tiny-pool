package org.tiny.pool.core.demo;

import com.alibaba.fastjson.JSON;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shichaoyang
 * @Description: 池化计算方式
 * @date 2020-07-09 20:21
 */
public class PoolCalculate {

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public static void main(String... args) throws Exception {

        //池化设置
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(2);
        config.setMaxWaitMillis(3000);

        //池化实例
        CalculatorPool calculatorPool = new CalculatorPool(config);

        Set<String> hashCodes = new HashSet<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    //从池子中拿出实例
                    Calculator calculator = calculatorPool.getCalculator();
                    String instanceCode = calculator.add(1,3);
                    //使用完毕，将实例归还池子
                    calculatorPool.returnCalculator(calculator);
                    hashCodes.add(instanceCode);
                    countDownLatch.countDown();
                }catch(Exception e){}
            });
        }
        countDownLatch.await();
        System.out.println("instance count:" + hashCodes.size() + "  instance list:" + JSON.toJSONString(hashCodes));
    }


}
