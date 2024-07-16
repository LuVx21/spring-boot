package org.luvx.boot.mars.web.useful;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Resource;

import org.luvx.boot.web.response.R;
import org.luvx.boot.mars.service.retrofit.GeekTimeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/useful")
@RestController
public class UsefulController {
    @Resource
    private GeekTimeService geekTimeService;

    @RequestMapping(value = {"/in"}, method = {RequestMethod.POST})
    public R<Object> index(@RequestBody Map<String, List<String>> param) {
        Set<String> left = Sets.newHashSet(param.getOrDefault("left", Collections.emptyList()));
        Set<String> right = Sets.newHashSet(param.getOrDefault("right", Collections.emptyList()));
        return R.success(Map.of(
                "left-right", Sets.difference(left, right),
                "right-left", Sets.difference(right, left),
                "left∩right", Sets.intersection(left, right)
        ));
    }

}
