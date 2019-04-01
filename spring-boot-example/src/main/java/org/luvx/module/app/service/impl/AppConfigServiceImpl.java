package org.luvx.module.app.service.impl;

import org.luvx.common.base.BaseServiceImpl;
import org.luvx.module.app.entity.AppConfig;
import org.luvx.module.app.mapper.AppConfigMapper;
import org.luvx.module.app.service.AppConfigService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: org.luvx.module.app.service.impl
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 17:34
 */
@Service
public class AppConfigServiceImpl extends BaseServiceImpl<AppConfigMapper, AppConfig>
        implements AppConfigService {
}
