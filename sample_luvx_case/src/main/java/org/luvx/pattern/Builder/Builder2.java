package org.luvx.pattern.Builder;


/**
 * 建造者模式(不存在指挥者的时候,实际上是将指挥者和抽象类合并在一起)
 * 建造者中有多个建造函数,用于创建复杂产品的组成部分
 * 还有一个返回创建对象的方法
 */
public abstract class Builder2 {

    protected Car car = new Car();

    public abstract void buildwheels();

    public abstract void buildengine();

    /**
     * 有参数的时候
     *
     * @return
     */
    public Car getCar() {
        this.buildwheels();
        this.buildengine();

        return car;
    }

}
