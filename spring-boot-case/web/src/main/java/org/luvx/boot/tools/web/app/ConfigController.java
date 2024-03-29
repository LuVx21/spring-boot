package org.luvx.boot.tools.web.app;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.runner.MainScheduler;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/config")
public class ConfigController {
    // @Resource
    // private MainScheduler mainScheduler;
    //
    // @RequestMapping(value = {"/cron"}, method = {RequestMethod.GET})
    // public R<Object> cron(@RequestParam("cron") String cron) {
    //     mainScheduler.setCron(cron);
    //     return R.success();
    // }
}
