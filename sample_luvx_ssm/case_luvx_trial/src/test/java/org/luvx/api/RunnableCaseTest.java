package org.luvx.api;

import org.junit.Ignore;
import org.junit.Test;


public class RunnableCaseTest {

    @Ignore
    public void run() {
        RunnableCase run = new RunnableCase();
        Thread thread = new Thread(run, "run");
        thread.start();
    }
}