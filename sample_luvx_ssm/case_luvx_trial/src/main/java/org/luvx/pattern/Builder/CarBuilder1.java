package org.luvx.pattern.Builder;

public class CarBuilder1 extends Builder1 {
    @Override
    public void buildwheels() {
        System.out.println("开始造轮子......");
        car.setWheels("4个轮子");
    }

    @Override
    public void buildengine() {
        System.out.println("开始造发动机......");
        car.setEngine("宝马引擎");
    }
}
