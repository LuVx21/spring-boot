package org.luvx.apache.common.lang;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @ClassName: org.luvx.apache.common.lang
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/27 11:06
 */
public class StringUtilsTest {

    @Test
    public void method0() {
        String dbName = "aa";
        String tableName = "bb";
        boolean flag = StringUtils.isNoneBlank(dbName, tableName);
        System.out.println(flag);
        Assert.assertEquals(flag, true);
    }


}
