package org.luvx.boot.tools.web.commonkv;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.luvx.boot.tools.dao.entity.CommonKeyValue;
import org.luvx.boot.tools.service.commonkv.CommonKeyValueService;
import org.luvx.boot.tools.service.commonkv.constant.CommonKVBizType;
import org.luvx.boot.web.response.R;
import org.luvx.coding.common.enums.EnumHasCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/commonkv")
public class CommonKeyValueController {
    @Resource
    private CommonKeyValueService commonKeyValueService;

    /**
     * 设置单值
     */
    @RequestMapping(value = {"set/single/{bizType}/{key}"}, method = {RequestMethod.POST}, consumes = MediaType.TEXT_PLAIN_VALUE)
    public R<Boolean> setSingleValue(@PathVariable("bizType") int bizType,
                                     @PathVariable("key") String key, @RequestBody String value
    ) {
        CommonKVBizType kvBizType = EnumHasCode.of(CommonKVBizType.class, bizType, CommonKVBizType.UNKNOWN);
        if (!kvBizType.isValidBizCode() || StringUtils.isBlank(key)) {
            return R.fail();
        }
        Class<?> valueClass = kvBizType.getValueClass();
        Object v = switch (valueClass.getName()) {
            case "java.lang.Integer" -> NumberUtils.toInt(value);
            case "java.lang.Long" -> NumberUtils.toLong(value);
            default -> value;
        };

        boolean b = commonKeyValueService.setValue(kvBizType, key, v);
        return R.success(b);
    }

    @RequestMapping(value = {"set/{bizType}/{key}"}, method = {RequestMethod.POST})
    public R<Boolean> setValue(@PathVariable("bizType") int bizType,
                               @PathVariable("key") String key, @RequestBody Object value
    ) {
        CommonKVBizType kvBizType = EnumHasCode.of(CommonKVBizType.class, bizType, CommonKVBizType.UNKNOWN);
        if (!kvBizType.isValidBizCode() || StringUtils.isBlank(key)) {
            return R.fail();
        }
        boolean b = commonKeyValueService.setValue(kvBizType, key, value);
        return R.success(b);
    }

    @RequestMapping(value = {"remove/{bizType}/{key}"}, method = {RequestMethod.GET})
    public R<Object> remove(@PathVariable("bizType") int bizType, @PathVariable("key") String key) {
        CommonKVBizType kvBizType = EnumHasCode.of(CommonKVBizType.class, bizType, CommonKVBizType.UNKNOWN);
        if (!kvBizType.isValidBizCode() || StringUtils.isBlank(key)) {
            return R.fail();
        }
        commonKeyValueService.remove(kvBizType, key);
        return R.success();
    }

    @RequestMapping(value = {"get/{bizType}/{key}"}, method = {RequestMethod.GET})
    public R<CommonKeyValue> get(@PathVariable("bizType") int bizType, @PathVariable("key") String key) {
        CommonKVBizType kvBizType = EnumHasCode.of(CommonKVBizType.class, bizType, CommonKVBizType.UNKNOWN);
        if (!kvBizType.isValidBizCode() || StringUtils.isBlank(key)) {
            return R.success();
        }
        CommonKeyValue v = commonKeyValueService.get(kvBizType, key).orElse(null);
        return R.success(v);
    }

    @RequestMapping(value = {"getData/{bizType}/{key}"}, method = {RequestMethod.GET})
    public R<Object> getData(@PathVariable("bizType") int bizType, @PathVariable("key") String key) {
        CommonKVBizType kvBizType = EnumHasCode.of(CommonKVBizType.class, bizType, CommonKVBizType.UNKNOWN);
        if (!kvBizType.isValidBizCode() || StringUtils.isBlank(key)) {
            return R.success();
        }
        Object v = commonKeyValueService.getData(kvBizType, key).orElse(null);
        return R.success(v);
    }
}
