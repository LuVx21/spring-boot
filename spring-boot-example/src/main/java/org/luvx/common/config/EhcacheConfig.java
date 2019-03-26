package org.luvx.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @ClassName: org.luvx.common.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/26 12:30
 */
@Configuration
@EnableCaching
public class EhcacheConfig {
    // @Bean
    // public EhCacheManagerFactoryBean cacheManagerFactoryBean() {
    //     EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
    //     bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
    //     bean.setShared(true);
    //     return bean;
    // }
    //
    // @Bean
    // public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
    //     return new EhCacheCacheManager(bean.getObject());
    // }
}
