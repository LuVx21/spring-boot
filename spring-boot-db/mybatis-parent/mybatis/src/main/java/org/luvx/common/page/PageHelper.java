package org.luvx.common.page;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

@Component
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class PageHelper implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 当前环境 MappedStatement，BoundSql，及sql取得
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String originalSql = boundSql.getSql().trim();
        Object parameterObject = boundSql.getParameterObject();

        // 处理dao参数传入@param格式报错问题
        if (!(parameterObject instanceof MapperMethod.ParamMap)) {
            // Page对象获取，“信使”到达拦截器！
            Page page = searchPageWithXpath(boundSql.getParameterObject(), ".", "page", "*/page");

            if (page != null) {
                // Page对象存在的场合，开始分页处理
                String countSql = getCountSql(originalSql);

                Connection connection = mappedStatement.getConfiguration()
                        .getEnvironment().getDataSource().getConnection();
                PreparedStatement countStmt = connection.prepareStatement(countSql);
                BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
                DefaultParameterHandler parameterHandler =
                        new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
                parameterHandler.setParameters(countStmt);
                ResultSet rs = countStmt.executeQuery();
                int totrecord = 0;
                if (rs.next()) {
                    totrecord = rs.getInt(1);
                }
                rs.close();
                countStmt.close();
                connection.close();

                // 分页计算
                page.setTotalCount(totrecord);
                page.setTotalPageCount(totrecord);

                // 对原始Sql追加limit
                int offset = (page.getCurrentPage() - 1) * page.getPageSize();
                StringBuffer sb = new StringBuffer();
                sb.append(originalSql).append(" limit ").append(offset).append(",").append(page.getPageSize());
                BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sb.toString());
                MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
                invocation.getArgs()[0] = newMs;
            }
        }
        return invocation.proceed();

    }

    /**
     * @Title: searchPageWithXpath @Description:
     * 根据给定的xpath查询Page对象 @param @param o @param @param xpaths @param @return
     * 设定文件 @return Page 返回类型 @throws
     */
    private Page searchPageWithXpath(Object o, String... xpaths) {
        JXPathContext context = JXPathContext.newContext(o);
        Object result;
        for (String xpath : xpaths) {
            try {
                result = context.selectSingleNode(xpath);
            } catch (JXPathNotFoundException e) {
                continue;
            }
            if (result instanceof Page) {
                return (Page) result;
            }
        }
        return null;
    }

    /**
     * @Title: copyFromMappedStatement @Description:
     * 复制MappedStatement对象 @param @param ms @param @param
     * newSqlSource @param @return 设定文件 @return MappedStatement 返回类型 @throws
     */
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource())
                .fetchSize(ms.getFetchSize())
                .statementType(ms.getStatementType())
                .keyGenerator(ms.getKeyGenerator())
                /// .keyProperty(ms.getKeyProperty())
                .timeout(ms.getTimeout())
                .parameterMap(ms.getParameterMap())
                .resultMaps(ms.getResultMaps())
                .resultSetType(ms.getResultSetType())
                .cache(ms.getCache())
                .flushCacheRequired(ms.isFlushCacheRequired())
                .useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * @Title: copyFromBoundSql @Description: 复制BoundSql对象 @param @param
     * ms @param @param boundSql @param @param sql @param @return 设定文件 @return
     * BoundSql 返回类型 @throws
     */
    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     */
    private String getCountSql(String sql) {
        return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";
    }

    public class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    @Override
    public void setProperties(Properties arg0) {
    }
}
