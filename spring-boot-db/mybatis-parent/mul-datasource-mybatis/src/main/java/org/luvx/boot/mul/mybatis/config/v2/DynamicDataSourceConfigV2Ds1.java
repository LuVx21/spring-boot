package org.luvx.boot.mul.mybatis.config.v2;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

import static org.luvx.boot.mul.mybatis.common.ConstValue.*;

@Configuration
@MapperScan(
        basePackages = {"org.luvx.boot.mul.mybatis.mapper.ds1"},
        sqlSessionFactoryRef = ds1SqlSessionFactory
)
public class DynamicDataSourceConfigV2Ds1 {
    @Bean(name = ds_ds1)
    @ConfigurationProperties(prefix = prefix_ds1)
    public DataSource getDateSourceDs1() {
        return new HikariDataSource();
    }

    @Bean(name = ds1SqlSessionFactory)
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier(ds_ds1) DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        String locationPattern = "classpath*:**/*Mapper.xml";
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(locationPattern));

        // org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // configuration.setMapUnderscoreToCamelCase(true);
        // bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean(name = ds1TransactionManager)
    public TransactionManager ds1TransactionManager(@Qualifier(ds_ds1) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}