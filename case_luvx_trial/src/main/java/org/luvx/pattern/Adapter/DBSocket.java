package org.luvx.pattern.Adapter;


/**
 * 符合德国标准的插座(两口圆形)
 */
public class DBSocket implements DBSocketInterface {

    public void powerWithTwoRound() {
        System.out.println("使用两口圆头的插孔供电");
    }
}
