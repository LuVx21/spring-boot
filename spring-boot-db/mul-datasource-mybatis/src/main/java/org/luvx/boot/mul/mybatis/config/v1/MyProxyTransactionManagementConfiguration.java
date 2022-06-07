package org.luvx.boot.mul.mybatis.config.v1;

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
                    if (name.contains("read")) {
                        attribute.setQualifier(ConstValue.readTransactionManager);
                    } else if (name.contains("write")) {
                        attribute.setQualifier(ConstValue.writeTransactionManager);
                    }
                    log.info("指定事务管理器:{} {}", name, attribute.getQualifier());
                }
                return attribute;
            }
        };
    }
}
