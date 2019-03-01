package org.luvx.api.thread;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName: org.luvx.api.thread
 * @Description: junit不支持多线程测试
 * @Author: Ren, Xie
 * @Date: 2019/2/28 11:06
 */
public class ExecutorServiceCaseTest {

    @Test
    public void run01() throws Exception {
        List<String> list = ImmutableList.of("begin", "code", "Guava", "Java");
        ExecutorServiceCase.run01(list);
        Thread.sleep(40 * 1000);
    }
}