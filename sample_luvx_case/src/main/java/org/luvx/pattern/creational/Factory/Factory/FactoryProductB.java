package org.luvx.pattern.creational.Factory.Factory;


import org.luvx.pattern.creational.Factory.Bean.FactoryInter;
import org.luvx.pattern.creational.Factory.Bean.Product;
import org.luvx.pattern.creational.Factory.Bean.ProductB;

/**
 * 工厂方法模式
 *
 * 为解决简单工厂模式中工厂类的臃肿问题,每种产品都由一个工厂创建
 */

public class FactoryProductB implements FactoryInter {

    @Override
    public Product getProduct() {
        return new ProductB();
    }
}
