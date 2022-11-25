package org.luvx.tools.web.retrofit.bili;

import jakarta.annotation.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import org.luvx.boot.web.response.R;
import org.luvx.tools.service.retrofit.bili.BiliService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("bili")
public class BiliController {
    @Resource
    private BiliService biliService;

    @RequestMapping(value = {"up/video"}, method = {RequestMethod.POST})
    public R<Object> upVideo(long upId) {
        Preconditions.checkArgument(upId > 0, "非法id");
        List<String> ids = biliService.upVideo(upId);
        return R.success(Map.of(
                "ids", ids
        ));
    }
}
