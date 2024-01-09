package org.luvx.boot.mul.mybatis.config.v1;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.*;
import org.luvx.boot.mul.mybatis.config.DSTypeContainer;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static java.lang.reflect.Proxy.newProxyInstance;

@Slf4j
public class CustomSqlSessionTemplate extends SqlSessionTemplate {
    private final SqlSessionFactory              sqlSessionFactory;
    @Getter
    private final ExecutorType                   executorType;
    private final SqlSession                     sqlSessionProxy;
    private final PersistenceExceptionTranslator exceptionTranslator;

    @Setter
    private Map<Object, SqlSessionFactory> targetSqlSessionFactorys;
    @Setter
    private SqlSessionFactory              defaultTargetSqlSessionFactory;

    public CustomSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        this(sqlSessionFactory, sqlSessionFactory.getConfiguration().getDefaultExecutorType());
    }

    public CustomSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType) {
        this(sqlSessionFactory, executorType, new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration()
                .getEnvironment().getDataSource(), true));
    }

    public CustomSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType,
                                    PersistenceExceptionTranslator exceptionTranslator) {
        super(sqlSessionFactory, executorType, exceptionTranslator);
        this.sqlSessionFactory = sqlSessionFactory;
        this.executorType = executorType;
        this.exceptionTranslator = exceptionTranslator;
        this.sqlSessionProxy = (SqlSession) newProxyInstance(
                SqlSessionFactory.class.getClassLoader(),
                new Class[]{SqlSession.class},
                new SqlSessionInterceptor());
        this.defaultTargetSqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        //当前数据源
        String currentDsName = DSTypeContainer.getDataSourceType().name();
        log.info("当前数据源:{}", currentDsName);
        SqlSessionFactory targetSqlSessionFactory = targetSqlSessionFactorys.get(currentDsName);
        if (targetSqlSessionFactory != null) {
            return targetSqlSessionFactory;
        } else if (defaultTargetSqlSessionFactory != null) {
            return defaultTargetSqlSessionFactory;
        }
        return this.sqlSessionFactory;
    }

    @Override
    public Configuration getConfiguration() {
        return this.getSqlSessionFactory().getConfiguration();
    }

    @Override
    public PersistenceExceptionTranslator getPersistenceExceptionTranslator() {
        return this.exceptionTranslator;
    }

    public <T> T selectOne(String statement) {
        return this.sqlSessionProxy.<T>selectOne(statement);
    }

    public <T> T selectOne(String statement, Object parameter) {
        return this.sqlSessionProxy.<T>selectOne(statement, parameter);
    }

    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return this.sqlSessionProxy.<K, V>selectMap(statement, mapKey);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return this.sqlSessionProxy.<K, V>selectMap(statement, parameter, mapKey);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return this.sqlSessionProxy.<K, V>selectMap(statement, parameter, mapKey, rowBounds);
    }

    public <E> List<E> selectList(String statement) {
        return this.sqlSessionProxy.<E>selectList(statement);
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return this.sqlSessionProxy.<E>selectList(statement, parameter);
    }

    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return this.sqlSessionProxy.<E>selectList(statement, parameter, rowBounds);
    }

    public void select(String statement, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, handler);
    }

    public void select(String statement, Object parameter, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, parameter, handler);
    }

    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, parameter, rowBounds, handler);
    }

    public int insert(String statement) {
        return this.sqlSessionProxy.insert(statement);
    }

    public int insert(String statement, Object parameter) {
        return this.sqlSessionProxy.insert(statement, parameter);
    }

    public int update(String statement) {
        return this.sqlSessionProxy.update(statement);
    }

    public int update(String statement, Object parameter) {
        return this.sqlSessionProxy.update(statement, parameter);
    }

    public int delete(String statement) {
        return this.sqlSessionProxy.delete(statement);
    }

    public int delete(String statement, Object parameter) {
        return this.sqlSessionProxy.delete(statement, parameter);
    }

    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }

    public void commit() {
        throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
    }

    public void commit(boolean force) {
        throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
    }

    public void rollback() {
        throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
    }

    public void rollback(boolean force) {
        throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
    }

    public void close() {
        throw new UnsupportedOperationException("Manual close is not allowed over a Spring managed SqlSession");
    }

    public void clearCache() {
        this.sqlSessionProxy.clearCache();
    }

    public Connection getConnection() {
        return this.sqlSessionProxy.getConnection();
    }

    public List<BatchResult> flushStatements() {
        return this.sqlSessionProxy.flushStatements();
    }

    /**
     * Proxy needed to route MyBatis method calls to the proper SqlSession got from Spring's Transaction Manager It also
     * unwraps exceptions thrown by {@code Method#invoke(Object, Object...)} to pass a {@code PersistenceException} to
     * the {@code PersistenceExceptionTranslator}.
     */
    private class SqlSessionInterceptor implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            final SqlSession sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory, executorType, exceptionTranslator);
            try {
                Object result = method.invoke(sqlSession, args);
                if (!SqlSessionUtils.isSqlSessionTransactional(sqlSession, getSqlSessionFactory())) {
                    // force commit even on non-dirty sessions because some databases require
                    // a commit/rollback before calling close()
                    sqlSession.commit(true);
                }
                return result;
            } catch (Throwable t) {
                Throwable unwrapped = ExceptionUtil.unwrapThrowable(t);
                if (exceptionTranslator != null && unwrapped instanceof PersistenceException) {
                    Throwable translated = exceptionTranslator.translateExceptionIfPossible((PersistenceException) unwrapped);
                    if (translated != null) {
                        unwrapped = translated;
                    }
                }
                throw unwrapped;
            } finally {
                SqlSessionUtils.closeSqlSession(sqlSession, getSqlSessionFactory());
            }
        }
    }

}