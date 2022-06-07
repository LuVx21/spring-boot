package org.luvx.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.luvx.handler.AutoFillMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("org.luvx.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    // @Bean
    // public PaginationInterceptor paginationInterceptor() {
    //     new MybatisPlusInterceptor();
    //     new PaginationInnerInterceptor()
    //     return new PaginationInterceptor();
    // }

    /**
     * <pre>
     *     实体类上使用, @KeySequence, 指定主键生成策略为 IdType.INPUT
     * </pre>
     */
    @Bean
    public IKeyGenerator keyGenerator() {
        return new IKeyGenerator() {
            @Override
            public String executeSql(String incrementerName) {
                return "select " + IdWorker.getId() + " from dual";
            }

            @Override
            public DbType dbType() {
                return DbType.MYSQL;
            }
        };
    }

    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.INPUT);

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        // 自动填充元属性处理器
        globalConfig.setMetaObjectHandler(new AutoFillMetaObjectHandler());
        // 设置全局 DB 配置
        globalConfig.setDbConfig(dbConfig);
        // 设置业务自定义业务前缀 ID 生成器
        // globalConfig.setIdentifierGenerator(new CustomBusinessIdGenerator());
        // globalConfig.setSqlInjector(new CustomSqlInjector());
        return globalConfig;
    }

}
