package org.luvx.pattern.Builder;

import org.junit.Test;

public class Builder1_2Test {

    @Test
    public void creatTest() {
        Builder1 builder = new CarBuilder1();
        Car car = Builder1.getCar(builder);
    }


    @Test
    public void createTest1(){
        Builder2 builder = new CarBuilder2();
        Car car = builder.getCar();
    }
}
