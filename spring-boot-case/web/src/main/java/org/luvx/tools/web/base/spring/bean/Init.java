package org.luvx.tools.web.base.spring.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

// @Configuration // 打开观察 bean 加载
public class Init {
    @Scope("singleton")
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public SmallBean smallBean() {
        return new SmallBean();
    }
}
