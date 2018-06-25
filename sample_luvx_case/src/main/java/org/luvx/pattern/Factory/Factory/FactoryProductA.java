package org.luvx.pattern.Factory.Factory;


import org.luvx.pattern.Factory.Bean.FactoryInter;
import org.luvx.pattern.Factory.Bean.Product;
import org.luvx.pattern.Factory.Bean.ProductA;
import org.luvx.pattern.Factory.Bean.ProductA;

/**
 * 工厂方法模式
 *
 * 为解决简单工厂模式中工厂类的臃肿问题,每种产品都由一个工厂创建
 * 基于这样的实现,增加产品时,同时创建该产品的工厂类
 * 因此,在产品很多的情况下,类的数量是原来的两倍(产品类和工厂类)
 */

public class FactoryProductA implements FactoryInter {

    @Override
    public Product getProduct() {
        return new ProductA();
    }
}
