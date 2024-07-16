package org.luvx.boot.mars.web.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
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
