package org.luvx.pattern.Builder;


/**
 * 创建有篷车的建造者
 */
public class CarBuilder extends Builder {
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

    @Override
    public void buildsail() {
        System.out.println("开始造车棚......");
        car.setSail("玛莎拉蒂车棚");
    }

    /**
     * 不是敞篷车,即需要建造棚
     *
     * @return
     */
    public boolean isConvertible() {
        return false;
    }
}
