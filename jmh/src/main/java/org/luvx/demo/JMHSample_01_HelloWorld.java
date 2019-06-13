package org.luvx.demo;

import lombok.AllArgsConstructor;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: org.luvx.demo
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/6/13 17:45
 */
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class JMHSample_01_HelloWorld {
    static List<Demo> demoList;

    static {
        demoList = new ArrayList();
        for (int i = 0; i < 10000; i++) {
            demoList.add(new Demo(i, "test"));
        }
    }

    @AllArgsConstructor
    static class Demo {
        int    id;
        String name;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testHashMapWithoutSize() {
        Map map = new HashMap();
        for (Demo demo : demoList) {
            map.put(demo.id, demo.name);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testHashMap() {
        Map map = new HashMap((int) (demoList.size() / 0.75f) + 1);
        for (Demo demo : demoList) {
            map.put(demo.id, demo.name);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_01_HelloWorld.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}