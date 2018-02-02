package org.luvx.pattern.Builder;

import org.junit.Test;

public class BuilderTest {

    @Test
    public void createTest() {
        Builder builder = new CarBuilder();
        Director director = new Director(builder);
        Car car = director.build();
        System.out.println(car.getWheels());
        System.out.println(car.getEngine());
        System.out.println(car.getSail());
    }

    /**
     * 实际相当于指定了两次建造者,而且是同一个建造者对象
     */
    @Test
    public void createTest1() {
        Builder builder = new CarBuilder();
        Director director = new Director(builder);
        Car car = director.build1(builder);
        System.out.println(car.getWheels());
        System.out.println(car.getEngine());
        System.out.println(car.getSail());
    }

    @Test
    public void createTest2() {
        Builder builder = new CarBuilder();
        Director director = new Director();
        director.setBuilder(builder);
        director.build();
    }

    @Test
    public void createTest3() {
        Builder builder = new CarBuilder();
        Director director = new Director();
        director.build1(builder);
    }
}
