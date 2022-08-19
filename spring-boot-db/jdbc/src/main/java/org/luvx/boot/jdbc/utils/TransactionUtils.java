package org.luvx.boot.jdbc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * @ClassName: org.luvx.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/7/16 16:44
 */
@Component
public class TransactionUtils {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Nullable
    public <T, R> R transact(Function<T, R> function, T paras) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            R apply = function.apply(paras);
            // int i = 2 / 0;
            transactionManager.commit(status);
            return apply;
        } catch (Exception e) {
            transactionManager.rollback(status);
            e.printStackTrace();
            return null;
        }
    }
}