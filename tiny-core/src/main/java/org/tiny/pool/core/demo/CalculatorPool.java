package org.tiny.pool.core.demo;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author shichaoyang
 * @Description:
 * @date 2020-07-09 20:29
 */
public class CalculatorPool {

    private GenericObjectPool<Calculator> calculatorPool;

    public CalculatorPool(GenericObjectPoolConfig config){
        CalculatorFactory calculatorFactory = new CalculatorFactory();
        calculatorPool = new GenericObjectPool<Calculator>(calculatorFactory,config);
    }

    /**
     * 从池中获取对象
     * @return
     * @throws Exception
     */
    public Calculator getCalculator() throws Exception{
        return calculatorPool.borrowObject();
    }

    /**
     * 将对象归还池中
     * @param calculator
     */
    public void returnCalculator(Calculator calculator){
        calculatorPool.returnObject(calculator);
    }
}
