package org.luvx.boot.mul.mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DSProperties.class)
@MapperScan(
        basePackages = {"org.luvx.boot.mul.mybatis.mapper"},
        sqlSessionFactoryRef = "SqlSessionFactory"
)
public class DynamicDataSourceConfig {

    @SuppressWarnings("unchecked")
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(DSProperties dsProperties) {
        Map targetDataSource = new HashMap<>(8);
        dsProperties.getDatasource()
                .forEach((k, v) -> targetDataSource.put(k, v.initializeDataSourceBuilder().build()));
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);

        // 设置默认的数据库
        DSTypeContainer.defaultType = (String) targetDataSource.keySet().stream().findFirst().get();
        dataSource.setDefaultTargetDataSource(targetDataSource.get(DSTypeContainer.defaultType));
        return dataSource;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        String locationPattern = "classpath:*Mapper.xml";
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(locationPattern));
        return bean.getObject();
    }
}