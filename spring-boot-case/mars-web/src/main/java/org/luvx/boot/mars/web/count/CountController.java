package org.luvx.boot.mars.web.count;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.luvx.boot.mars.rpc.common.count.CountOperateType;
import org.luvx.boot.mars.rpc.common.count.CountType;
import org.luvx.boot.mars.rpc.sdk.user.UserCountType;
import org.luvx.boot.mars.service.count.impl.CountService;
import org.luvx.boot.web.response.R;
import org.luvx.coding.common.enums.EnumHasCode;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/mars/count")
public class CountController {
    @Resource
    private CountService countService;

    @GetMapping(value = "{countIds}")
    public R<Object> getByIds(@PathVariable("countIds") Collection<Long> countIds) {
        var r = countService.getByIds(countIds);
        return R.success(r);
    }

    @GetMapping(value = "{countType}/{countIds}")
    public R<Object> getByType(@PathVariable Collection<Long> countIds, @PathVariable int countType) {
        UserCountType type = EnumHasCode.of(UserCountType.class, countType);
        var r = countService.getByType(type, countIds);
        return R.success(r);
    }

    @PostMapping(value = "operate/{countId}")
    public R<Object> operate(@PathVariable long countId, @RequestBody Map<String, Object> params) {
        int event = MapUtils.getIntValue(params, "event");
        List<CountType> types = ((List<Integer>) MapUtils.getObject(params, "types")).stream()
                .map(i -> (CountType) EnumHasCode.of(UserCountType.class, i))
                .toList();
        int value = MapUtils.getIntValue(params, "value");

        CountOperateType operateType = EnumHasCode.of(CountOperateType.class, event);
        countService.operate(operateType, countId, types, value);
        return R.success("ok");
    }

    @PostMapping(value = "asyncOperate/{countId}")
    public R<Object> asyncOperate(@PathVariable long countId, @RequestBody Map<String, Object> params) {
        int event = MapUtils.getIntValue(params, "event");
        List<CountType> types = ((List<Integer>) MapUtils.getObject(params, "types")).stream()
                .map(i -> (CountType) EnumHasCode.of(UserCountType.class, i))
                .toList();
        int value = MapUtils.getIntValue(params, "value");

        CountOperateType operateType = EnumHasCode.of(CountOperateType.class, event);
        countService.asyncOperate(operateType, countId, types, value);
        return R.success("ok");
    }

    @PostMapping(value = "batchSet/{countId}")
    public R<Object> batchSet(@PathVariable long countId, @RequestBody Map<Integer, Integer> params) {
        Map<CountType, Integer> countMap = params.entrySet().stream()
                .collect(Collectors.toMap(e -> EnumHasCode.of(UserCountType.class, e.getKey()),
                        Map.Entry::getValue
                ));
        countService.batchSet(countId, countMap);
        return R.success("ok");
    }
}
