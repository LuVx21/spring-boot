package org.luvx.tools.service.commonkv;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.luvx.common.util.JsonUtils;
import org.luvx.tools.dao.entity.CommonKeyValue;
import org.luvx.tools.dao.mapper.CommonKeyValueMapper;
import org.luvx.tools.service.commonkv.constant.KVBizType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mybatis.mapper.example.Example;

@Service
public class CommonKeyValueServiceImpl implements CommonKeyValueService {
    @Autowired
    private CommonKeyValueMapper repository;

    @Override
    public <T> boolean setValue(KVBizType bizType, String key, T value) {
        CommonKeyValue kv = get(bizType, key);
        if (kv == null) {
            kv = new CommonKeyValue();
            kv.setBizType(bizType.getCode());
            kv.setCommonKey(key);
            kv.setCommonValue(JsonUtils.toJson(value));
            kv.setCreateTime(System.currentTimeMillis());
            repository.insertSelective(kv);
        } else {
            kv.setCommonValue(JsonUtils.toJson(value));
            kv.setUpdateTime(System.currentTimeMillis());
            repository.updateByPrimaryKey(kv);
        }
        return true;
    }

    @Override
    public boolean setOrIncrValue(KVBizType bizType, String key, long incr) {
        if (bizType.valueClass() != Integer.class && bizType.valueClass() != Long.class) {
            return false;
        }

        CommonKeyValue kv = get(bizType, key);
        if (kv == null) {
            kv = new CommonKeyValue();
            kv.setBizType(bizType.getCode());
            kv.setCommonKey(key);
            kv.setCommonValue(JsonUtils.toJson(incr));
            kv.setCreateTime(System.currentTimeMillis());
            repository.insertSelective(kv);
        } else {
            long l = NumberUtils.toLong(kv.getCommonValue());
            l += incr;
            kv.setCommonValue(JsonUtils.toJson(l));
            kv.setUpdateTime(System.currentTimeMillis());
            repository.updateByPrimaryKey(kv);
        }

        return true;
    }

    @Override
    public Map<String, CommonKeyValue> get(KVBizType bizType, Collection<String> keys) {
        Example<CommonKeyValue> example = new Example<>();
        example.createCriteria()
                .andEqualTo(CommonKeyValue::getBizType, bizType.getCode())
                .andIn(CommonKeyValue::getCommonKey, keys);
        return repository.selectByExample(example).stream()
                .collect(Collectors.toMap(CommonKeyValue::getCommonKey, Function.identity(), (a, b) -> b));
    }
}
