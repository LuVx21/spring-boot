package org.luvx.boot.mul.mybatis.config.v1;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.luvx.boot.mul.mybatis.config.DS;
import org.luvx.boot.mul.mybatis.config.DynamicDataSource;
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
import java.util.Map;

@Configuration
@MapperScan(
        basePackages = {"org.luvx.boot.mul.mybatis.mapper"},
        sqlSessionTemplateRef = "sqlSessionTemplate"
)
public class DynamicDataSourceConfigV1 {
    public static final String readTransactionManager  = "readTransactionManager";
    public static final String writeTransactionManager = "writeTransactionManager";

    private final String ds_read  = "read";
    private final String ds_write = "write";

    @Bean(name = ds_read)
    @ConfigurationProperties(prefix = "spring.dynamic.datasource.read")
    public DataSource getDateSourceRead() {
        return new HikariDataSource();
    }

    @Bean(name = ds_write)
    @ConfigurationProperties(prefix = "spring.dynamic.datasource.write")
    public DataSource getDateSourceWrite() {
        return new HikariDataSource();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier(ds_read) DataSource read,
                                        @Qualifier(ds_write) DataSource write) {
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(Map.of(
                DS.DSType.read, read,
                DS.DSType.write, write
        ));
        dataSource.setDefaultTargetDataSource(write);
        return dataSource;
    }

    @Bean(name = "readSqlSessionFactory")
    public SqlSessionFactory readSqlSessionFactory(@Qualifier(ds_read) DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    @Bean(name = "writeSqlSessionFactory")
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

    @Bean(name = readTransactionManager)
    public TransactionManager readTransactionManager(@Qualifier(ds_read) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = writeTransactionManager)
    public TransactionManager writeTransactionManager(@Qualifier(ds_write) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    public CustomSqlSessionTemplate sqlSessionTemplate(@Qualifier("readSqlSessionFactory") SqlSessionFactory readSqlSessionFactory,
                                                       @Qualifier("writeSqlSessionFactory") SqlSessionFactory writeSqlSessionFactory) {
        CustomSqlSessionTemplate customSqlSessionTemplate = new CustomSqlSessionTemplate(writeSqlSessionFactory);
        customSqlSessionTemplate.setTargetSqlSessionFactorys(Map.of(
                "read", readSqlSessionFactory,
                "write", writeSqlSessionFactory
        ));
        customSqlSessionTemplate.setDefaultTargetSqlSessionFactory(writeSqlSessionFactory);
        return customSqlSessionTemplate;
    }
}