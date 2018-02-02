package org.luvx.pattern.Adapter;

import org.junit.Test;

public class AdapterTest {

    @Test
    public void test1() {
        GBSocketInterface gbSocket = new GBSocket();
        SocketAdapter socketAdapter = new SocketAdapter(gbSocket);
        socketAdapter.powerWithTwoRound();
    }

    @Test
    public void test2() {
        SocketAdapter1 socketAdapter = new SocketAdapter1();
        socketAdapter.powerWithTwoRound();
    }
}