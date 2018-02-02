package org.luvx.pattern.Factory.Simple;

import org.luvx.pattern.Factory.Bean.Product;
import org.luvx.pattern.Factory.Bean.ProductA;
import org.luvx.pattern.Factory.Bean.ProductB;
import org.luvx.pattern.Factory.Bean.Product;
import org.luvx.pattern.Factory.Bean.ProductA;
import org.luvx.pattern.Factory.Bean.ProductB;

/**
 * 简单工厂模式,也被称为静态工厂方法模式
 *
 * 新增一个方法时,需要创建一个产品类继承Product类,并实现抽象方法
 * 当产品数量很多的情况下,下面getProduct方法很臃肿,条件语句很多
 */

public class Factory {

    //静态工厂方法
    public static Product getProduct(String arg) {
        Product product = null;
        if (arg.equalsIgnoreCase("A")) {
            product = new ProductA();
            //初始化设置product
        }
        else if (arg.equalsIgnoreCase("B")) {
            product = new ProductB();
            //初始化设置product
        }
        return product;
    }
}
