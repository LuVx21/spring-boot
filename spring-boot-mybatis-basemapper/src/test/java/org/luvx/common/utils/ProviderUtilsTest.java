package org.luvx.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.luvx.entity.User;

import java.util.Arrays;

/**
 * @ClassName: org.luvx.common.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/6/6 16:40
 */
// @RunWith(SpringRunner.class)
// @SpringBootTest
public class ProviderUtilsTest {

    @Test
    public void parseObjectTest() {
        User user = User.builder().userId(10010L).userName("a").password("b").age(10).build();
        JSONObject obj = ProviderUtils.parseObject(user);
        System.out.println(obj);
    }

    @Test
    public void getPrimaryKeyTest() {
        String pk = ProviderUtils.getPrimaryKey(User.class);
        System.out.println(pk);
    }


    @Test
    public void getReadVariablesTest() {
        String[] strs = ProviderUtils.getReadFields(User.class);
        System.out.println(Arrays.toString(strs));
    }

    @Test
    public void getWriteVariablesTest() {
        String[] strs = ProviderUtils.getWriteFields(User.class);
        System.out.println(Arrays.toString(strs));
    }
}