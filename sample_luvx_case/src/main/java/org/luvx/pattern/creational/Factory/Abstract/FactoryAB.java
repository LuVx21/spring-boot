package org.luvx.pattern.creational.Factory.Abstract;

import org.luvx.pattern.creational.Factory.Bean.AbstractFactory;
import org.luvx.pattern.creational.Factory.Bean.ProductA;
import org.luvx.pattern.creational.Factory.Bean.ProductB;


/**
 * 工厂方法模式下,同一个产品对应2个类存在
 * 考虑将工厂进行合并,即一个工厂生产多种产品
 * 每一个工厂类中都提供多个工厂方法
 * 这些方法创建的产品属于同一个产品族
 * 如同一家公司生产的电视和冰箱
 * 增加一个产品族,新建一个工厂类即可,此时符号开闭原则
 * 向一个产品族中添加一个产品,需要修改现有工厂类,不符合开闭原则
 * 实现上抽象工厂模式无法在不违反该原则的前提下实现更改
 */
public class FactoryAB extends AbstractFactory {

    @Override
    public ProductA getProductA() {
        return new ProductA();
    }

    @Override
    public ProductB getProductB() {
        return new ProductB();
    }
}
