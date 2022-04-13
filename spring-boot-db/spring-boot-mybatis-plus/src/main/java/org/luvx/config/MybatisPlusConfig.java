package org.luvx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Ren, Xie
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        new MybatisPlusInterceptor();
//                new PaginationInnerInterceptor()
//        return new PaginationInterceptor();
//    }
}
