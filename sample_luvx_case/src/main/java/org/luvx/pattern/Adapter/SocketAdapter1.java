package org.luvx.pattern.Adapter;


/**
 * 类适配器
 * 与对象适配器不同的是,可以认为是新生产一个同时符合国标和德标的新插座
 * 即,为中国人能在德国使用的新品种插座
 */


public class SocketAdapter1 implements GBSocketInterface, DBSocketInterface {

    @Override
    public void powerWithTwoRound() {
        this.powerWithThreeFlat();
    }

    @Override
    public void powerWithThreeFlat() {
        System.out.println("使用三项扁头插孔供电");
    }
}