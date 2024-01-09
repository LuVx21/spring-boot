package org.luvx.boot.jdbc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jakarta.annotation.Nullable;
import java.util.function.Function;

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