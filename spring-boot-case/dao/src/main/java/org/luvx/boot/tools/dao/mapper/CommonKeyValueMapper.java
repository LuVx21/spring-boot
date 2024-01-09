package org.luvx.boot.tools.dao.mapper;

import org.luvx.boot.tools.dao.entity.CommonKeyValue;

import io.mybatis.mapper.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface CommonKeyValueMapper extends Mapper<CommonKeyValue, Long> {
}