package org.luvx.api.thread.entity;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;

/**
 * @ClassName: org.luvx.api.thread.thread
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 14:10
 */
@AllArgsConstructor
public class CallableCase implements Callable<String> {
    private String name;

    @Override
    public String call() {
        Task.execute(name);
        return name;
    }
}
