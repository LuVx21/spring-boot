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
        basePackages = {"org.luvx.boot.mul.mybatis.mapper.write"},
        sqlSessionFactoryRef = writeSqlSessionFactory
)
public class DynamicDataSourceConfigV2Write {
    @Bean(name = ds_write)
    @ConfigurationProperties(prefix = prefix_write)
    public DataSource getDateSourceWrite() {
        return new HikariDataSource();
    }

    @Bean(name = writeSqlSessionFactory)
    public SqlSessionFactory writeSqlSessionFactory(@Qualifier(ds_write) DataSource dataSource) throws Exception {
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

    @Bean(name = writeTransactionManager)
    public TransactionManager writeTransactionManager(@Qualifier(ds_write) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}