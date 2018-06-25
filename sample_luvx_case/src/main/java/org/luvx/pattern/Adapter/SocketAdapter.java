package org.luvx.pattern.Adapter;


/**
 * 对象适配器
 * 可以看做是在德标插座内部加上国标插座的零件
 */


public class SocketAdapter implements DBSocketInterface {
    // 符合国标接口
    private GBSocketInterface gbSocket;

    /**
     *以符合国标的创建适配器
     */
    public SocketAdapter(GBSocketInterface gbSocket) {
        this.gbSocket = gbSocket;
    }

    /**
     * 使其符合德国标准
     */
    @Override
    public void powerWithTwoRound() {
        gbSocket.powerWithThreeFlat();
    }

}