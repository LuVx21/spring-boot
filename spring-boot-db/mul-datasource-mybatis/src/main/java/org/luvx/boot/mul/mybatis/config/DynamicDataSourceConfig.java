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
import java.util.Map;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toMap;

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
        Map<Object, Object> targetDataSource = dsProperties.getDatasource().entrySet().stream()
                .collect(toMap(Entry::getKey, e -> e.getValue().initializeDataSourceBuilder().build()));
        DS.DSType defaultType = DS.DSType.write;
        if (!targetDataSource.containsKey(defaultType.name())) {
            DS.DSType.valueOf((String) targetDataSource.keySet().stream().findFirst().get());
        }
        Object defaultTargetDataSource = targetDataSource.get(defaultType.name());

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(defaultTargetDataSource);
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