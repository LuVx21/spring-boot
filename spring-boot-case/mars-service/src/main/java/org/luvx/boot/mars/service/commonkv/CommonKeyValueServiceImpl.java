package org.luvx.boot.mars.service.commonkv;

import io.mybatis.mapper.example.Example;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.luvx.boot.mars.dao.entity.CommonKeyValue;
import org.luvx.boot.mars.dao.mapper.CommonKeyValueMapper;
import org.luvx.boot.mars.service.commonkv.constant.KVBizType;
import org.luvx.coding.common.util.JsonUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class CommonKeyValueServiceImpl implements CommonKeyValueService {
    private final String ssss = "common_value = %s(common_value, %s)";

    @Resource
    private CommonKeyValueMapper mapper;

    @Override
    public <T> boolean setValue(KVBizType bizType, String key, @Nonnull T value, boolean onlyIfAbsent) {
        checkArgument(StringUtils.isNotBlank(key), "key不可为空");
        checkArgument(ObjectUtils.isNotEmpty(value), "value 不可为空");
        checkArgument(bizType.getValueClass().isAssignableFrom(value.getClass()),
                "value类型和业务场景不匹配->预期:%s, 实际->%s", bizType.getValueClass(), value.getClass());

        Optional<CommonKeyValue> op = get(bizType, key);
        String commonValue = JsonUtils.toJson(value);
        op.ifPresentOrElse(kv -> {
            if (onlyIfAbsent) {
                return;
            }
            kv.setCommonValue(commonValue);
            kv.setUpdateTime(System.currentTimeMillis());
            mapper.updateByPrimaryKey(kv);
        }, () -> {
            CommonKeyValue kv = new CommonKeyValue();
            kv.setBizType(bizType.getBizType());
            kv.setCommonKey(key);
            kv.setCommonValue(commonValue);
            kv.setCreateTime(System.currentTimeMillis());
            mapper.insertSelective(kv);
        });
        return true;
    }

    @Override
    public boolean setOrIncrValue(KVBizType bizType, String key, long incr) {
        if (incr == 0) {
            return false;
        }
        if (bizType.getValueClass() != Integer.class && bizType.getValueClass() != Long.class) {
            return false;
        }

        Optional<CommonKeyValue> op = get(bizType, key);
        op.ifPresentOrElse(kv -> {
            long l = NumberUtils.toLong(kv.getCommonValue());
            l += incr;
            kv.setCommonValue(JsonUtils.toJson(l));
            kv.setUpdateTime(System.currentTimeMillis());
            mapper.updateByPrimaryKey(kv);
        }, () -> {
            CommonKeyValue kv = new CommonKeyValue();
            kv.setBizType(bizType.getBizType());
            kv.setCommonKey(key);
            kv.setCommonValue(JsonUtils.toJson(incr));
            kv.setCreateTime(System.currentTimeMillis());
            mapper.insertSelective(kv);
        });

        return true;
    }

    @Override
    public void remove(KVBizType bizType, String... keys) {
        if (ArrayUtils.isEmpty(keys)) {
            return;
        }
        CommonKeyValue kv = new CommonKeyValue();
        kv.setBizType(bizType.getBizType());
        for (String key : keys) {
            kv.setCommonKey(key);
            mapper.delete(kv);
        }
    }

    @Override
    public void setValueItem(KVBizType bizType, String key, Map<String, Object> kvs, boolean onlyIfAbsent) {
        checkArgument(StringUtils.isNotBlank(key), "key不可为空");
        checkArgument(MapUtils.isNotEmpty(kvs), "itemKey不可为空");

        String collect = kvs.entrySet().stream()
                .map(e -> "'$." + e.getKey() + "', " + JsonUtils.toJson(e.getValue()))
                .collect(Collectors.joining(", "));

        String s = onlyIfAbsent ? "JSON_INSERT" : "JSON_SET";
        String setSql = ssss.formatted(s, collect);
        Example<CommonKeyValue> example = new Example<>();
        example.set(setSql).createCriteria()
                .andEqualTo(CommonKeyValue::getBizType, bizType.getBizType())
                .andEqualTo(CommonKeyValue::getCommonKey, key);
        mapper.updateByExampleSetValues(example);
    }

    @Override
    public void removeValueItem(KVBizType bizType, String key, String... itemKey) {
        checkArgument(StringUtils.isNotBlank(key), "key不可为空");
        checkArgument(ArrayUtils.isNotEmpty(itemKey), "itemKey不可为空");

        String keys = Arrays.stream(itemKey)
                .map(k -> "'$." + k + "'")
                .collect(Collectors.joining(", "));
        String setSql = ssss.formatted("JSON_REMOVE", keys);
        Example<CommonKeyValue> example = new Example<>();
        example.set(setSql).createCriteria()
                .andEqualTo(CommonKeyValue::getBizType, bizType.getBizType())
                .andEqualTo(CommonKeyValue::getCommonKey, key);
        mapper.updateByExampleSetValues(example);
    }

    @Override
    public Map<String, CommonKeyValue> get(KVBizType bizType, Collection<String> keys) {
        Example<CommonKeyValue> example = new Example<>();
        example.createCriteria()
                .andEqualTo(CommonKeyValue::getBizType, bizType.getBizType())
                .andEqualTo(CommonKeyValue::getInvalid, 0)
                .andIn(CommonKeyValue::getCommonKey, keys);
        return mapper.selectByExample(example).stream()
                .collect(Collectors.toMap(CommonKeyValue::getCommonKey, Function.identity(), (a, b) -> b));
    }

    @Override
    public void enable(KVBizType bizType, String key) {
        onOff(bizType, key, true);
    }

    @Override
    public void disable(KVBizType bizType, String key) {
        onOff(bizType, key, false);
    }

    private void onOff(KVBizType bizType, String key, boolean valid) {
        int invalid = valid ? 0 : 1;
        Example<CommonKeyValue> example = new Example<>();
        example.set("invalid = " + invalid).createCriteria()
                .andEqualTo(CommonKeyValue::getBizType, bizType.getBizType())
                .andEqualTo(CommonKeyValue::getCommonKey, key);
        mapper.updateByExampleSetValues(example);
    }
}
