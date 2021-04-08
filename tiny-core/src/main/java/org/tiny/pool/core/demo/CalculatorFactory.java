package org.tiny.pool.core.demo;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author shichaoyang
 * @Description:
 * @date 2020-07-09 20:38
 */
public class CalculatorFactory extends BasePooledObjectFactory<Calculator> {

    /**
     * 创建calculator实例
     * @return
     * @throws Exception
     */
    @Override
    public Calculator create() throws Exception {
        return new Calculator();
    }

    /**
     * 将calculator对象打包成可以被池化管理的对象，之后calculator对象就可以在池子中被任意的使用和归还了
     * @param calculator
     * @return
     */
    @Override
    public PooledObject<Calculator> wrap(Calculator calculator) {
        return new DefaultPooledObject<>(calculator);
    }
}
