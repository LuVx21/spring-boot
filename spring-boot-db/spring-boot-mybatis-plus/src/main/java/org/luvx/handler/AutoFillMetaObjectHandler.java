package org.luvx.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;

@Slf4j
public class AutoFillMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        TableInfo tableInfo = findTableInfo(metaObject);
        ResultMap resultMap = tableInfo.getConfiguration().getResultMap(tableInfo.getResultMap());
        log.info("自动填充:{} {}", tableInfo, resultMap);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
