package org.luvx.pattern.Builder;


/**
 * 建造者模式
 * 建造者中有多个建造函数,用于创建复杂产品的组成部分
 * 还有一个返回创建对象的方法
 */
public abstract class Builder {

    protected Car car = new Car();

    public abstract void buildwheels();

    public abstract void buildengine();

    public abstract void buildsail();


    //钩子方法

    /**
     * 钩子方法,可以精细控制创建负载对象
     * 当指定是敞篷车时,buildsail()则不会执行
     * 是否有篷(敞篷车)
     *
     * @return
     */
    public boolean isConvertible() {
        return true;
    }

    public Car getCar() {
        return car;
    }

}
