package org.luvx.tools.dao.commonkv;

import io.mybatis.mapper.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface CommonKeyValueRepository extends Mapper<CommonKeyValue, Long> {
}