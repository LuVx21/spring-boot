package org.luvx.module.data.mapper;

import org.junit.Test;
import org.luvx.base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: org.luvx.module.data.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/1 20:27
 */
public class DataMapperTest extends BaseTest {

    @Autowired
    DataMapper mapper;

    @Test
    public void listTables() {
        // List<Map<String, Object>> list = mapper.listTables();
        // for (Map<String, Object> map : list) {
        //     System.out.println(map.size());
        // }
    }
}