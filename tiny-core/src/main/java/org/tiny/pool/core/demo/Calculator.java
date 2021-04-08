package org.tiny.pool.core.demo;

/**
 * @author shichaoyang
 * @Description: 计算器
 * @date 2020-07-09 20:15
 */
public class Calculator {

    public String add(int x, int y) {
        String instance = "Calculator[" + this.hashCode() + "]";
        System.out.println(instance + " The add result: " + x + y);
        return instance;
    }

}
