package org.luvx.api.thread.entity;

import lombok.AllArgsConstructor;

/**
 * @ClassName: org.luvx.api.thread.thread
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 14:09
 */
@AllArgsConstructor
public class RunnableCase implements Runnable {
    private String name;

    @Override
    public void run() {
        Task.execute(name);
    }
}
