package org.luvx.boot.mul.mybatis.config.config;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mul.mybatis.common.ConstValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import java.lang.reflect.Method;

@Slf4j
@Configuration
public class MyProxyTransactionManagementConfiguration extends ProxyTransactionManagementConfiguration {
    /**
     * 声明式事务中不指定事务管理器, 此方法根据一定规则指定事务管理器
     * 无论使用数据源 a 还是 b, 都可以根据规则找到对应的事务管理器
     * <p>
     * 重写了事务注解属性解析器的内容
     * 让其根据包名动态的获取事务管理器名称
     * 以达到不修改Service代码的前提下保证多数据源下的事务能够正常运行
     *
     * @return 事务注解属性解析器
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Override
    public TransactionAttributeSource transactionAttributeSource() {
        return new AnnotationTransactionAttributeSource() {
            @Override
            public TransactionAttribute getTransactionAttribute(Method method, @Nullable Class<?> targetClass) {
                DefaultTransactionAttribute attribute = (DefaultTransactionAttribute) super.getTransactionAttribute(method, targetClass);
                if (attribute != null
                    // && attribute.getQualifier() == null
                ) {
                    // 获取方法归属类的全名
                    String name = method.getName();
                    if (name.startsWith(ConstValue.ds_ds1)) {
                        attribute.setQualifier(ConstValue.ds1TransactionManager);
                    } else if (name.startsWith(ConstValue.ds_ds2)) {
                        attribute.setQualifier(ConstValue.ds2TransactionManager);
                    }
                    log.info("指定事务管理器:{} {}", name, attribute.getQualifier());
                }
                return attribute;
            }
        };
    }
}
