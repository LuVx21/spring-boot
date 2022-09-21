package org.luvx.tools.service.commonkv;

import org.apache.commons.lang3.math.NumberUtils;
import org.luvx.common.util.JsonUtils;
import org.luvx.tools.dao.commonkv.CommonKeyValue;
import org.luvx.tools.dao.commonkv.CommonKeyValueRepository;
import org.luvx.tools.service.commonkv.constant.KVBizType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.Collection;
import java.util.Map;

@Service
public class CommonKeyValueServiceImpl implements CommonKeyValueService {
    @Autowired
    private CommonKeyValueRepository repository;

    @Override
    public <T> boolean setValue(KVBizType bizType, String key, T value) {
        try {
            CommonKeyValue record = new CommonKeyValue();
            record.setBizType(bizType.getCode());
            record.setKey(key);
            record.setValue(JsonUtils.toJson(value));
            record.setCreateTime(System.currentTimeMillis());
            // repository.save(record);
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public boolean setOrIncrValue(KVBizType bizType, String key, long incr) {
        if (bizType.valueClass() != Integer.class && bizType.valueClass() != Long.class) {
            return false;
        }
        CommonKeyValue kv = get(bizType, key);
        if (kv != null) {
            kv = new CommonKeyValue();
        } else {
            long l = NumberUtils.toLong(kv.getValue());
            l += incr;
            kv.setValue(JsonUtils.toJson(l));
        }
        // repository.save(kv);
        return false;
    }

    @Override
    public Map<String, CommonKeyValue> get(KVBizType bizType, Collection<String> keys) {
        // repository.findOne(Example.of());

        // Iterable<CommonKeyValue> all = repository.findAll();
        return null;
    }
}
