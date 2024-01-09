package org.luvx.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.luvx.config.handler.AutoFillMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan(MybatisPlusConfig.mapperPackage)
public class MybatisPlusConfig {
    static final String MAPPER_LOCATIONS   = "classpath*:**/*Mapper.xml";
    static final String typeAliasesPackage = "org.luvx.entity";
    static final String mapperPackage      = "org.luvx.mapper";

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, @Qualifier("globalConfig") GlobalConfig globalConfig) throws Exception {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.addInterceptor(mybatisPlusInterceptor());
        configuration.setCallSettersOnNulls(true);

        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfiguration(configuration);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATIONS));
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);
        return factoryBean.getObject();
    }

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
                return "select " + IdWorker.getId();
            }

            @Override
            public DbType dbType() {
                return DbType.MYSQL;
            }
        };
    }

    @Bean("globalConfig")
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

    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 物理分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 针对 update 和 delete 语句 作用: 阻止恶意的全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }
}
