package org.luvx.boot.tools.dao.mapper;

import io.mybatis.mapper.Mapper;
import org.luvx.boot.tools.dao.entity.OssFile;

@org.apache.ibatis.annotations.Mapper
public interface OssFileMapper extends Mapper<OssFile, Long> {
}