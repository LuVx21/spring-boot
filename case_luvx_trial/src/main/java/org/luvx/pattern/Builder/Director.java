package org.luvx.pattern.Builder;


/**
 * 该类也可以合并到Buidler抽象类中去
 * 在抽象类中提供静态build方法构想复杂对象
 */
public class Director {

    private Builder builder;

    public Director() {

    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public Director(Builder builder) {
        this.builder = builder;
    }


    public Car build() {
        builder.buildwheels();
        builder.buildengine();
        if (!builder.isConvertible()) {
            builder.buildsail();
        }
        return builder.getCar();
    }

    public Car build1(Builder builder) {
        builder.buildwheels();
        builder.buildengine();
        // 使用钩子方法
        if (!builder.isConvertible()) {
            builder.buildsail();
        }

        return builder.getCar();
    }
}
