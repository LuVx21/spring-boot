package org.luvx.boot.mars.dao.mapper;

import org.luvx.boot.mars.dao.entity.CommonKeyValue;

import io.mybatis.mapper.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface CommonKeyValueMapper extends Mapper<CommonKeyValue, Long> {
}