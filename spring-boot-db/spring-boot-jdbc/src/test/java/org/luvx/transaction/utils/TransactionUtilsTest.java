package org.luvx.transaction.utils;

import org.junit.Test;
import org.luvx.transaction.service.UserService;
import org.luvx.transaction.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: org.luvx.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/7/16 16:47
 */
public class TransactionUtilsTest {
    @Autowired
    TransactionUtils transactionUtil;
    @Autowired
    UserService      userService;

    @Test
    public void transact() {
        boolean result = transactionUtil.transact(s -> userService.method());
    }
}