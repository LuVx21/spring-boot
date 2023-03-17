package org.luvx.boot.tools.web.retrofit.bili;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

import org.luvx.boot.web.response.R;
import org.luvx.boot.tools.service.retrofit.BiliService;
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
