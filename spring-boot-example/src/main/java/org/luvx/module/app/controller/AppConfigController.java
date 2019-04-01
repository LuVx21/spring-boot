package org.luvx.module.app.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.luvx.module.app.entity.AppConfig;
import org.luvx.module.app.service.AppConfigService;
import org.luvx.module.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: org.luvx.module.app.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/31 17:26
 */
@Api(value = "AppConfigController", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RestController
@RequestMapping(value = "/config")
public class AppConfigController {
    @Autowired
    private AppConfigService appConfigService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveAppConfig(@RequestBody AppConfig appConfig) {
        appConfigService.save(appConfig);
        return JSON.toJSONString(appConfig);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAppConfig(@RequestBody AppConfig appConfig) {
        appConfig.setValid(false);
        // 逻辑删除
        appConfigService.updateById(appConfig);
        /// 物理删除
        /// appConfigService.removeById(appConfig.getConfigId());
        return JSON.toJSONString(appConfig);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAppConfig(@RequestBody AppConfig appConfig) {
        appConfigService.update(appConfig,
                new QueryWrapper<AppConfig>().eq("config_key", appConfig.getConfigKey())
        );
        R<AppConfig> r = Response.ok(appConfig);
        return JSON.toJSONString(r);
    }

    @RequestMapping(value = "/select/{configKey}", method = RequestMethod.GET)
    public String selectAppConfig(@PathVariable String configKey) {
        AppConfig appConfig = appConfigService.getOne(
                new QueryWrapper<AppConfig>().eq("config_key", configKey)
        );

        R<AppConfig> r = Response.ok(appConfig);
        return JSON.toJSONString(r);
    }
}
