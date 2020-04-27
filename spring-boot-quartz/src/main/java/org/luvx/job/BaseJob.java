package org.luvx.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.task
 * @Description: 不继承类, 不实现接口定义job
 * @Author: Ren, Xie
 * @Date: 2019/3/7 11:06
 */
@Slf4j
@Component
public abstract class BaseJob {
    public static final String METHOD_NAME = "execute";

    /**
     * jod执行入口
     */
    public abstract void execute();
}
